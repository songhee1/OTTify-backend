package tavebalak.OTTify.program.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import tavebalak.OTTify.error.ErrorCode;
import tavebalak.OTTify.error.exception.NotFoundException;
import tavebalak.OTTify.genre.entity.Genre;
import tavebalak.OTTify.genre.entity.ProgramGenre;
import tavebalak.OTTify.genre.repository.GenreRepository;
import tavebalak.OTTify.genre.repository.ProgramGenreRepository;
import tavebalak.OTTify.program.dto.*;
import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.program.entity.ProgramType;
import tavebalak.OTTify.program.repository.ProgramRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProgramShowAndSaveService {
    private final ProgramRepository programRepository;
    private final WebClient webClient;
    private final GenreRepository genreRepository;
    private final ProgramGenreRepository programGenreRepository;



    @Transactional
    public TrendingResponseDto showTrending(){
        TrendingResponseDto trendingResponseDto=new TrendingResponseDto();
        OpenApiSearchTrendingDto openApiSearchTrendingDto=getTrendingProgram("day");
        openApiSearchTrendingDto.getResults().stream().forEach(trendingDayProgramInfo -> {
                    if(trendingDayProgramInfo.getMedia_type().equals("movie")){
                        Program program=saveProgramAndGetProgram(trendingDayProgramInfo, ProgramType.Movie);
                        if(trendingResponseDto.getProgramTrendingDayInfos().size()<8) {
                            makeTrendingResponse(trendingResponseDto, program,trendingDayProgramInfo,"day");
                        }
                    }
                    if(trendingDayProgramInfo.getMedia_type().equals("tv")){
                        Program program=saveProgramAndGetProgram(trendingDayProgramInfo,ProgramType.TV);
                        if(trendingResponseDto.getProgramTrendingDayInfos().size()<8) {
                            makeTrendingResponse(trendingResponseDto, program,trendingDayProgramInfo,"day");
                        }
                    }
                }
        );
        openApiSearchTrendingDto=getTrendingProgram("week");
        openApiSearchTrendingDto.getResults().stream().forEach(openApiProgram -> {
            if(openApiProgram.getMedia_type().equals("movie")){
                Program program=saveProgramAndGetProgram(openApiProgram,ProgramType.Movie);
                if(trendingResponseDto.getProgramTrendingWeekInfos().size()<8) {
                    makeTrendingResponse(trendingResponseDto, program,openApiProgram,"week");
                }
            }
            if(openApiProgram.getMedia_type().equals("tv")){
                Program program=saveProgramAndGetProgram(openApiProgram,ProgramType.TV);
                if(trendingResponseDto.getProgramTrendingWeekInfos().size()<8) {
                    makeTrendingResponse(trendingResponseDto, program,openApiProgram,"week");
                }
            }
        });

        return trendingResponseDto;
    }

    // 가져온 트렌딩 프로그램 정보 + 부가 정보를 OpenApiSearchTrendingDto 로 매핑합니다

    private OpenApiSearchTrendingDto getTrendingProgram(String choiceArrange){
        OpenApiSearchTrendingDto openApiSearchTrendingDto=webClient.get()
                .uri("/trending/all/"+choiceArrange+"?language=ko")
                .retrieve()
                .bodyToMono(OpenApiSearchTrendingDto.class)
                .block();
        return openApiSearchTrendingDto;
    }


    // 프로그램이 저장되어 있는 경우 저장된 프로그램을 반환하고, 아닌 경우 저장하고 반환합니다.

    public Program saveProgramAndGetProgram(SearchTrendingOpenApiProgramInfo searchTrendingOpenApiProgramInfo, ProgramType programType){
        Program program;
        if(!programRepository.existsByTmDbProgramIdAndAndType(searchTrendingOpenApiProgramInfo.getId(),programType)){
            program=apiProgramToProgram(searchTrendingOpenApiProgramInfo,programType);
            programRepository.save(program);
        }
        else{
            program=programRepository.findByTmDbProgramIdAndType(searchTrendingOpenApiProgramInfo.getId(),programType);
        }
        return program;
    }

    //받아온 api 정보를 이용해 우리가 만든 Program 에 필요한 값을 넣어줍니다.
    private Program apiProgramToProgram(SearchTrendingOpenApiProgramInfo searchTrendingOpenApiProgramInfo,ProgramType programType){
        // 공통 관련 빌드
        Program.ProgramBuilder programBuilder = Program.builder().tmDbProgramId(searchTrendingOpenApiProgramInfo.getId())
                .type(programType)
                .posterPath(searchTrendingOpenApiProgramInfo.getPoster_path());

        //영화일 경우
        if(programType==ProgramType.Movie){
            programBuilder.title(searchTrendingOpenApiProgramInfo.getTitle());
            String createdDate=searchTrendingOpenApiProgramInfo.getRelease_date();
            programBuilder.createdYear(createdDate.length()>=4 ? createdDate.substring(0,4) :"");
        }
        //tv 일 경우
        else{
            programBuilder.title(searchTrendingOpenApiProgramInfo.getName());
            String createdDate=searchTrendingOpenApiProgramInfo.getFirst_air_date();
            programBuilder.createdYear(createdDate.length()>=4 ? createdDate.substring(0,4) : "");
        }

        Program program=programBuilder.build();

        //연관관계 편의 메서드를 통한 장르 저장
        searchTrendingOpenApiProgramInfo.getGenre_ids().forEach(gi->{
            Genre genre=genreRepository.findByTmDbGenreId(gi).orElseThrow(()->new NotFoundException(ErrorCode.GENRE_NOT_FOUND));
            program.addGenre(genre);
        });
        return program;
    }
    //프론트에 반환할 트렌딩 리스폰스를 만듭니다.
    private void makeTrendingResponse(TrendingResponseDto trendingResponseDto,Program p,SearchTrendingOpenApiProgramInfo searchTrendingOpenApiProgram,String range){
        List<Long> genreIdList=searchTrendingOpenApiProgram.getGenre_ids();
        String firstGenreName;
        if(!genreIdList.isEmpty()){
            ProgramGenre programGenre=programGenreRepository.findByGenreIdAndProgramIdWithFetch(genreIdList.get(0),p.getId());
            firstGenreName=programGenre.getGenre().getName();
        }
        else{
            firstGenreName=null;
        }

        if(range.equals("day")){
            trendingResponseDto.getProgramTrendingDayInfos().add(new ProgramTrendingDayInfo(p.getId(),p.getTitle(),p.getCreatedYear(), firstGenreName, searchTrendingOpenApiProgram.getBackdrop_path()));
        }
        else{
            trendingResponseDto.getProgramTrendingWeekInfos().add(new ProgramTrendingWeekInfo(p.getId(),p.getTitle(),p.getCreatedYear(), firstGenreName, searchTrendingOpenApiProgram.getPoster_path()));
        }

    }



}
