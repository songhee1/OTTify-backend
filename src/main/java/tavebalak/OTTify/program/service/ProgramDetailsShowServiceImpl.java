package tavebalak.OTTify.program.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import tavebalak.OTTify.error.ErrorCode;
import tavebalak.OTTify.error.exception.NotFoundException;
import tavebalak.OTTify.genre.repository.GenreRepository;
import tavebalak.OTTify.program.dto.programDetails.Response.ProgramDetailResponse;
import tavebalak.OTTify.program.dto.programDetails.Response.ProgramResponseDto;
import tavebalak.OTTify.program.dto.programDetails.openApiRequest.personDetails.Cast;
import tavebalak.OTTify.program.dto.programDetails.openApiRequest.personDetails.OAProgramCreditsDto;
import tavebalak.OTTify.program.dto.programDetails.openApiRequest.programDetailRequest.OAMovieDetailsDto;
import tavebalak.OTTify.program.dto.programDetails.openApiRequest.programDetailRequest.OAProgramDetailsDto;
import tavebalak.OTTify.program.dto.programDetails.openApiRequest.programDetailRequest.OATvDetailsDto;
import tavebalak.OTTify.program.dto.programDetails.openApiRequest.providerDetails.OACountryDetailsDto;
import tavebalak.OTTify.program.dto.programDetails.openApiRequest.providerDetails.OAProgramProviderDto;
import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.program.entity.ProgramType;
import tavebalak.OTTify.program.repository.ProgramRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProgramDetailsShowServiceImpl implements ProgramDetailsShowService {

    private final WebClient webClient;
    private final GenreRepository genreRepository;
    private final ProgramRepository programRepository;


    @Override
    public ProgramResponseDto showDetails(Long programId) {
        Program program = programRepository.findById(programId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PROGRAM_NOT_FOUND));

        //API 요청 첫번째: 프로그램 상세 정보
        OAProgramDetailsDto OAProgramDetailsDto = getProgramDetails(program.getTmDbProgramId(),
            program.getType());
        ProgramDetailResponse programDetailResponse = createProgramDetailResponse(
            OAProgramDetailsDto);

        //API 요청 두번째: 사람 상세 정보
        OAProgramCreditsDto oaProgramCreditsDto = getCreditsDto(program.getTmDbProgramId(),
            program.getType());
        changeActorAndDirectorToKorea(oaProgramCreditsDto);

        //API 요청 세번째: Provider 상세 정보
        OAProgramProviderDto oaProgramProviderDto = getProviderDto(program.getTmDbProgramId(),
            program.getType());
        OACountryDetailsDto kr = oaProgramProviderDto.getResults().get("KR");

        //DTO 에 반환
        int buySize = kr.getBuy() == null ? 0 : kr.getBuy().size();
        int rentSize = kr.getRent() == null ? 0 : kr.getRent().size();
        int flatrateSize = kr.getFlatrate() == null ? 0 : kr.getFlatrate().size();

        kr.initSize(buySize, rentSize, flatrateSize);

        // 사용자에게 보내줄 DTO
        ProgramResponseDto programResponseDto = new ProgramResponseDto(programDetailResponse,
            oaProgramCreditsDto, kr);
        return programResponseDto;

    }

    //프로그램 상세 정보를 open api 를 통해서 요청
    private OAProgramDetailsDto getProgramDetails(Long tmDbId, ProgramType programType) {
        if (programType == ProgramType.Movie) {
            OAMovieDetailsDto oaMovieDetailsDto = webClient.get()
                .uri("/movie/" + tmDbId + "?language=ko")
                .retrieve()
                .bodyToMono(OAMovieDetailsDto.class)
                .block();

            return oaMovieDetailsDto;
        } else {
            OATvDetailsDto oaTvDetailsDto = webClient.get()
                .uri("/tv/" + tmDbId + "?language=ko")
                .retrieve()
                .bodyToMono(OATvDetailsDto.class)
                .block();

            return oaTvDetailsDto;
        }
    }

    //TV와 영화의 API 반환값 차이 제거 위해 새로 DTO 반환값 설계
    private ProgramDetailResponse createProgramDetailResponse(
        OAProgramDetailsDto oaProgramDetailsDto) {

        //장르 이름 변환
        List<Long> changeGenreIds = new ArrayList<>();
        changeGenreIds.add(10759L);
        changeGenreIds.add(10762L);
        changeGenreIds.add(10763L);
        changeGenreIds.add(10764L);
        changeGenreIds.add(10765L);
        changeGenreIds.add(10766L);
        changeGenreIds.add(10767L);
        changeGenreIds.add(10768L);

        oaProgramDetailsDto.getTmDbGenreInfos().stream().forEach(g -> {
            if (changeGenreIds.contains(g.getId())) {
                g.changeName(genreRepository.findByTmDbGenreId(g.getId())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.PROGRAM_GENRE_NOT_FOUND))
                    .getName());
            }
        });

        //장르 이름을 DTO에 반환하기 위해 추출!
        List<String> genreName = new ArrayList<>();

        oaProgramDetailsDto.getTmDbGenreInfos().stream().forEach(g -> {
            genreName.add(g.getName());
        });

        // originalCountryName 추출 :영어 밖에 안됨 ㅠ
        String originalCountryName = (oaProgramDetailsDto.getProductionCountries() == null
            || oaProgramDetailsDto.getProductionCountries().isEmpty())
            ? null : oaProgramDetailsDto.getProductionCountries().get(0).getName();

        //programDetails 가 Movie 일 경우
        if (oaProgramDetailsDto instanceof OAMovieDetailsDto) {
            OAMovieDetailsDto oaMovieDetailsDto = (OAMovieDetailsDto) oaProgramDetailsDto;

            return ProgramDetailResponse.builder()
                .createdDate(oaMovieDetailsDto.getReleaseDate())
                .country(originalCountryName)
                .originalTitle(oaMovieDetailsDto.getOriginalTitle())
                .overview(oaMovieDetailsDto.getOverview())
                .title(oaMovieDetailsDto.getTitle())
                .posterPath(oaMovieDetailsDto.getPoster_path())
                .genreName(genreName)
                .tagline(oaMovieDetailsDto.getTagline())
                .backDropPath(oaMovieDetailsDto.getBackdrop_path())
                .build();
        }
        //programDetails 가 TV 일 경우
        else {
            OATvDetailsDto oaTvDetailsDto = (OATvDetailsDto) oaProgramDetailsDto;

            return ProgramDetailResponse.builder()
                .createdDate(oaTvDetailsDto.getFirstAirDate())
                .country(originalCountryName)
                .originalTitle(oaTvDetailsDto.getOriginalName())
                .overview(oaTvDetailsDto.getOverview())
                .title(oaTvDetailsDto.getName())
                .posterPath(oaTvDetailsDto.getPoster_path())
                .genreName(genreName)
                .tagline(oaProgramDetailsDto.getTagline())
                .backDropPath(oaProgramDetailsDto.getBackdrop_path())
                .build();
        }
    }


    //프로그램의 배우 가져오기
    private OAProgramCreditsDto getCreditsDto(Long tmDbProgramId, ProgramType programType) {
        RequestHeadersUriSpec<?> requestHeadersUriSpec = webClient.get();

        if (programType == ProgramType.Movie) {
            requestHeadersUriSpec.uri("/movie/" + tmDbProgramId + "/credits");
        } else {
            requestHeadersUriSpec.uri("/tv/" + tmDbProgramId + "/credits");
        }

        return requestHeadersUriSpec
            .retrieve()
            .bodyToMono(OAProgramCreditsDto.class)
            .block();

    }

    private void changeActorAndDirectorToKorea(OAProgramCreditsDto oaProgramCreditsDto) {
        Map<String, String> changeName = new HashMap<>();
        changeName.put("Acting", "배우");
        changeName.put("Directing", "감독");

        //배우 또는 감독인 경우 한글 이름으로 변화 시킵니다.
        oaProgramCreditsDto.getCast().stream().forEach(cast -> {
            if (changeName.containsKey(cast.getKnownForDepartment())) {
                cast.changeKnownForDepartMent(changeName.get(cast.getKnownForDepartment()));
            }
            if (changeName.containsKey(cast.getDepartment())) {
                cast.changeDepartment(changeName.get(cast.getDepartment()));
            }
        });

        //OpenApi 가 받아오는 것을 감독 -> 배우 -> 다른 것 순으로 받아옵니다.
        List<Cast> casts = oaProgramCreditsDto.getCast().stream().sorted((e1, e2) -> {
            if (e1.getKnownForDepartment().equals("감독")) {
                return -1;
            } else if (e1.getKnownForDepartment().equals("배우")) {
                if (e2.getKnownForDepartment().equals("감독")) {
                    return 1;
                } else {
                    return -1;
                }
            } else {
                if (e2.getKnownForDepartment().equals("배우") || e2.getKnownForDepartment()
                    .equals("감독")) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }).collect(Collectors.toList());

        //DTO 순서를 바꾼 것으로 변화시킵니다.
        oaProgramCreditsDto.changeCast(casts);
    }

    //프로그램의 Provider 가지고 오기

    private OAProgramProviderDto getProviderDto(Long tmDbProgramId, ProgramType programType) {

        RequestHeadersUriSpec<?> requestHeadersUriSpec = webClient.get();
        if (programType == ProgramType.Movie) {
            requestHeadersUriSpec.uri("/movie/" + tmDbProgramId + "/watch/providers");
        } else {
            requestHeadersUriSpec.uri("/tv/" + tmDbProgramId + "/watch/providers");
        }
        return requestHeadersUriSpec
            .retrieve()
            .bodyToMono(OAProgramProviderDto.class)
            .block();

    }
}
