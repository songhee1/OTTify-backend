package tavebalak.OTTify.program.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import tavebalak.OTTify.error.ErrorCode;
import tavebalak.OTTify.error.exception.NotFoundException;
import tavebalak.OTTify.genre.entity.Genre;
import tavebalak.OTTify.genre.repository.GenreRepository;
import tavebalak.OTTify.genre.repository.UserGenreRepository;
import tavebalak.OTTify.program.dto.programDetails.Response.ProgramDetailResponse;
import tavebalak.OTTify.program.dto.programDetails.Response.ProgramProviderListResponseDto;
import tavebalak.OTTify.program.dto.programDetails.Response.ProgramProviderResponseDto;
import tavebalak.OTTify.program.dto.programDetails.Response.ProgramResponseDto;
import tavebalak.OTTify.program.dto.programDetails.openApiRequest.personDetails.Cast;
import tavebalak.OTTify.program.dto.programDetails.openApiRequest.personDetails.OAProgramCreditsDto;
import tavebalak.OTTify.program.dto.programDetails.openApiRequest.programDetailRequest.OAMovieDetailsDto;
import tavebalak.OTTify.program.dto.programDetails.openApiRequest.programDetailRequest.OAProgramDetailsDto;
import tavebalak.OTTify.program.dto.programDetails.openApiRequest.programDetailRequest.OATvDetailsDto;
import tavebalak.OTTify.program.dto.programDetails.openApiRequest.providerDetails.OACountryDetailsDto;
import tavebalak.OTTify.program.dto.programDetails.openApiRequest.providerDetails.OAProgramProviderDto;
import tavebalak.OTTify.program.dto.response.UserSpecificRatingResponseDto;
import tavebalak.OTTify.program.entity.Ott;
import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.program.entity.ProgramType;
import tavebalak.OTTify.program.repository.OttRepository;
import tavebalak.OTTify.program.repository.ProgramRepository;
import tavebalak.OTTify.review.repository.ReviewRepository;
import tavebalak.OTTify.user.entity.User;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProgramDetailsShowServiceImpl implements ProgramDetailsShowService {

    private final WebClient webClient;
    private final GenreRepository genreRepository;
    private final ProgramRepository programRepository;
    private final OttRepository ottRepository;
    private final UserGenreRepository userGenreRepository;
    private final ReviewRepository reviewRepository;


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
        Optional<OACountryDetailsDto> kr = Optional.ofNullable(
            oaProgramProviderDto.getResults().get("KR"));

        //사용자에게 보낼 DTO 를 만들고 OTT 의 이름을 한글로 변환시키는 작업을 수행합니다.
        Optional<ProgramProviderListResponseDto> programProviderListResponseDto = kr.map(
            oaCountryDetailsDto -> changeOTTtoKoreanAndMakeProviderResponseDto(
                oaCountryDetailsDto));

        // 사용자에게 보내줄 DTO
        ProgramResponseDto programResponseDto = new ProgramResponseDto(programDetailResponse,
            oaProgramCreditsDto, programProviderListResponseDto.orElse(null),
            program.getAverageRating());
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
        Map<String, String> changeCastName = new HashMap<>();
        changeCastName.put("Acting", "배우");
        changeCastName.put("Directing", "감독");

        //Acting 또는 Directing인 경우만 가져오도록 합니다.
        List<Cast> castsDirectingAndActing = oaProgramCreditsDto.getCast().stream()
            .filter(cast -> changeCastName.containsKey(cast.getKnownForDepartment()))
            .collect(Collectors.toList());

        //Acting 과 Directing 을 배우와 감독으로 한국이름으로 바꿉니다.
        castsDirectingAndActing.stream().forEach(cast -> {
            cast.changeKnownForDepartMent(changeCastName.get(cast.getKnownForDepartment()));
        });

        // 감독 -> 배우 순으로 정렬합니다.
        List<Cast> casts = castsDirectingAndActing.stream().sorted((e1, e2) -> {
            if (e1.getKnownForDepartment().equals("감독")) {
                if (e2.getKnownForDepartment().equals("배우")) {
                    return -1;
                } else {
                    return 0;
                }
            } else {
                if (e2.getKnownForDepartment().equals("감독")) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }).collect(Collectors.toList());

        //DTO 순서를 바꾼 것으로 변화시킵니다.
        oaProgramCreditsDto.changeCast(casts);

        //crew에서 Directing만 거릅니다.

        Map<String, String> changeCrewName = new HashMap<>();
        changeCrewName.put("Directing", "감독");

        List<Cast> crewDirecting = oaProgramCreditsDto.getCrew().stream().filter(cast ->
            changeCrewName.containsKey(cast.getKnownForDepartment())
        ).collect(Collectors.toList());

        crewDirecting.stream().forEach(cast -> {
            cast.changeKnownForDepartMent(changeCrewName.get(cast.getKnownForDepartment()));
        });

        oaProgramCreditsDto.changeCrew(crewDirecting);


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

    private ProgramProviderListResponseDto changeOTTtoKoreanAndMakeProviderResponseDto(
        OACountryDetailsDto oaCountryDetailsDto) {

        List<ProgramProviderResponseDto> buy = new ArrayList<>();
        List<ProgramProviderResponseDto> rent = new ArrayList<>();
        List<ProgramProviderResponseDto> streaming = new ArrayList<>();

        Optional.ofNullable(oaCountryDetailsDto.getBuy()).ifPresent(oaProviderDetailsDtos -> {
            oaProviderDetailsDtos.stream().filter(oaProviderDetailsDto ->
                ottRepository.existsByTmDbProviderId(oaProviderDetailsDto.getProvider_id())
            ).forEach(oaProviderDetailsDto -> {
                Optional<Ott> ott = ottRepository.findByTmDbProviderId(
                    oaProviderDetailsDto.getProvider_id());
                buy.add(new ProgramProviderResponseDto(oaProviderDetailsDto.getLogo_path(),
                    ott.orElse(null).getName()));
            });
        });

        Optional.ofNullable(oaCountryDetailsDto.getRent()).ifPresent(oaProviderDetailsDtos -> {
            oaProviderDetailsDtos.stream().filter(oaProviderDetailsDto ->
                ottRepository.existsByTmDbProviderId(oaProviderDetailsDto.getProvider_id())
            ).forEach(oaProviderDetailsDto -> {
                Optional<Ott> ott = ottRepository.findByTmDbProviderId(
                    oaProviderDetailsDto.getProvider_id());
                rent.add(new ProgramProviderResponseDto(oaProviderDetailsDto.getLogo_path(),
                    ott.orElse(null).getName()));
            });
        });

        Optional.ofNullable(oaCountryDetailsDto.getFlatrate()).ifPresent(oaProviderDetailsDtos -> {
            oaProviderDetailsDtos.stream().filter(oaProviderDetailsDto ->
                ottRepository.existsByTmDbProviderId(oaProviderDetailsDto.getProvider_id())
            ).forEach(oaProviderDetailsDto -> {
                Optional<Ott> ott = ottRepository.findByTmDbProviderId(
                    oaProviderDetailsDto.getProvider_id());
                streaming.add(new ProgramProviderResponseDto(oaProviderDetailsDto.getLogo_path(),
                    ott.orElse(null).getName()));
            });
        });

        return ProgramProviderListResponseDto.builder()
            .buy(buy)
            .rent(rent)
            .streaming(streaming)
            .buySize(buy.size())
            .rentSize(rent.size())
            .streamingSize(streaming.size())
            .build();

    }

    //user 의 first genre 에 맞춘 평점을 보여줍니다. 지금 현재는 컨트롤러에서 사용하고 있지만 나중에 프로그램 상세 페이지에서 같이 넣으려고 합니다

    @Override
    public UserSpecificRatingResponseDto showUserSpecificRating(User user, Long programId) {
        Program program = programRepository.findById(programId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PROGRAM_NOT_FOUND));

        Genre usersFirstGenre = userGenreRepository.findByUserAndIsFirst(user, true)
            .orElseThrow(() -> new NotFoundException(ErrorCode.USER_FIRST_GENRE_NOT_FOUND))
            .getGenre();

        int userSpecificGenreCount = reviewRepository.countByMyGenreName(usersFirstGenre.getName(),
            program);

        Double sumRating = reviewRepository.sumReviewRatingByGenreName(usersFirstGenre.getName(),
            program);

        double userSpecificReviewRatingSum = (sumRating != null) ? sumRating : 0.0;

        double avg =
            userSpecificGenreCount == 0 ? 0 : userSpecificReviewRatingSum / userSpecificGenreCount;

        return new UserSpecificRatingResponseDto(usersFirstGenre.getName(), avg);
    }
}
