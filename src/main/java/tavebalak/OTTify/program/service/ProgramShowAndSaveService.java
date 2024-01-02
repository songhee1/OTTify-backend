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
import tavebalak.OTTify.program.dto.searchTrending.Response.*;
import tavebalak.OTTify.program.dto.searchTrending.openApi.OpenApiSearchTrendingDto;
import tavebalak.OTTify.program.dto.searchTrending.openApi.SearchTrendingOpenApiProgramInfo;
import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.program.entity.ProgramType;
import tavebalak.OTTify.program.repository.ProgramRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProgramShowAndSaveService {
    private final ProgramRepository programRepository;
    private final WebClient webClient;
    private final GenreRepository genreRepository;
    private final ProgramGenreRepository programGenreRepository;


    // 트렌딩 관련 저장 및 조회
    @Transactional
    public TrendingResponseDto showTrending(){

        //actor 타입을 거르기 위함
        List<String> includeList = new ArrayList<>();
        includeList.add("movie");
        includeList.add("tv");


        //open api 를 통해 day trending 데이터 가져오기
        OpenApiSearchTrendingDto openApiSearchTrendingDto=getTrendingProgram("day");

        //trendingDayInfo 만들기
        List<ProgramTrendingDayInfo> programTrendingDayInfos = new ArrayList<>();

        openApiSearchTrendingDto.getResults().stream().forEach(trendingDayProgramInfo -> {
                    if(includeList.contains(trendingDayProgramInfo.getMedia_type())){

                        Program program = (trendingDayProgramInfo.getMedia_type().equals("movie"))
                                ? saveProgramAndGetProgram(trendingDayProgramInfo, ProgramType.Movie)
                                : saveProgramAndGetProgram(trendingDayProgramInfo, ProgramType.TV);

                        if(programTrendingDayInfos.size()<8){
                            String firstGenreName = getProgramFirstGenre(trendingDayProgramInfo.getGenre_ids());
                            programTrendingDayInfos.add(new ProgramTrendingDayInfo(program.getId(),program.getTitle(),program.getCreatedYear(), firstGenreName,trendingDayProgramInfo.getBackdrop_path()));
                        }
                    }
                }
        );

        //open api 를 통해 week trending 가져오기
        openApiSearchTrendingDto=getTrendingProgram("week");


        //trendingWeek 만들기
        List<ProgramTrendingWeekInfo> programTrendingWeekInfos = new ArrayList<>();
        openApiSearchTrendingDto.getResults().stream().forEach(trendingWeekProgramInfo -> {

            if(includeList.contains(trendingWeekProgramInfo.getMedia_type())){
                Program program = (trendingWeekProgramInfo.getMedia_type().equals("movie"))
                        ? saveProgramAndGetProgram(trendingWeekProgramInfo, ProgramType.Movie)
                        : saveProgramAndGetProgram(trendingWeekProgramInfo, ProgramType.TV);

                if(programTrendingWeekInfos.size()<8){
                    String firstGenreName = getProgramFirstGenre(trendingWeekProgramInfo.getGenre_ids());
                    programTrendingWeekInfos.add(new ProgramTrendingWeekInfo(program.getId(),program.getTitle(),program.getCreatedYear(), firstGenreName,trendingWeekProgramInfo.getPoster_path()));
                }
            }
        });

        return new TrendingResponseDto(programTrendingDayInfos,programTrendingWeekInfos);
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
            programBuilder.createdYear(createdDate.length()>=4 ? createdDate.substring(0,4) :null);
        }
        //tv 일 경우
        else{
            programBuilder.title(searchTrendingOpenApiProgramInfo.getName());
            String createdDate=searchTrendingOpenApiProgramInfo.getFirst_air_date();
            programBuilder.createdYear(createdDate.length()>=4 ? createdDate.substring(0,4) : null);
        }

        Program program=programBuilder.build();

        //연관관계 편의 메서드를 통한 장르 저장
        searchTrendingOpenApiProgramInfo.getGenre_ids().forEach(gi->{
            Genre genre=genreRepository.findByTmDbGenreId(gi).orElseThrow(()->new NotFoundException(ErrorCode.PROGRAM_GENRE_NOT_FOUND));
            program.addGenre(genre);
        });
        return program;
    }


    //프로그램의 대표 장르 , program 의 첫번쨰 넘어오는 장르 이름을 가지고 옵니다. 프로그램에 장르가 명시되지 않은 경우 null 을 넣습니다.
    private String getProgramFirstGenre(List<Long> genreIdList){
        return genreIdList.stream().findFirst().map(gi->{
            return genreRepository.findByTmDbGenreId(gi).orElseThrow(()->new NotFoundException(ErrorCode.PROGRAM_GENRE_NOT_FOUND)).getName();
        }).orElse(null);
    }



    //첫번째 검색시 기본적으로 검색할 때 TV 갯수, MOVIE 갯수, TV를 검색한 1페이지를 반환합니다.

    @Transactional
    public SearchResponseDto searchByName(String name){
        //tvResponse 를 검색한 DTO 를 반환합니다.
        SearchTvResponseDto searchTvResponseDto = searchByTvName(name,1);

        //영화를 검색하며 DB에 저장하되 반환하는 것은 갯수 뿐입니다.
        OpenApiSearchTrendingDto movieSearchList=getSearchProgram("movie",name,1);
        movieSearchList.getResults().stream().forEach(openApiProgram -> {
            saveProgramAndGetProgram(openApiProgram,ProgramType.Movie);
        });

        int movieCount = movieSearchList.getTotal_results();


        return new SearchResponseDto(movieCount,searchTvResponseDto.getTotalResults(),searchTvResponseDto);
    }

    //영화를 page 별로 검색할 수 있습니다.
    @Transactional
    public SearchMovieResponseDto searchByMovieName(String name,int page){

        //movieSearchInfos 에 관련 내용을 담습니다
        List<ProgramSearchInfo> movieSearchInfos = new ArrayList<>();

        //open api를 통해 검색 결과를 받아옵니다.
        OpenApiSearchTrendingDto movieSearchList=getSearchProgram("movie",name,page);

        //저장하고 DTO를 설계합니다
        movieSearchList.getResults().stream().forEach(movieProgramInfo->{
            Program program=saveProgramAndGetProgram(movieProgramInfo,ProgramType.Movie);
            String firstGenreName = getProgramFirstGenre(movieProgramInfo.getGenre_ids());
            movieSearchInfos.add(new ProgramSearchInfo(program.getId(),program.getTitle(),movieProgramInfo.getRelease_date(),firstGenreName,program.getPosterPath(),movieProgramInfo.getOverview()));
        });

        return new SearchMovieResponseDto(movieSearchInfos,movieSearchList.getPage(),movieSearchList.getTotal_pages(),movieSearchList.getTotal_results());
    }

    //TV를 page 별로 검색할 수 있습니다.
    @Transactional
    public SearchTvResponseDto searchByTvName(String name,int page){

        //tvSearchInfos 에 관련 내용을 담습니다.
        List<ProgramSearchInfo> tvSearchInfos = new ArrayList<>();

        // //open api를 통해 검색 결과를 받아옵니다.
        OpenApiSearchTrendingDto tvSearchList=getSearchProgram("tv",name,page);

        //저장하고 DTO를 설계합니다
        tvSearchList.getResults().stream().forEach(tvProgramInfo->{
            Program program=saveProgramAndGetProgram(tvProgramInfo,ProgramType.TV);
            String firstGenreName = getProgramFirstGenre(tvProgramInfo.getGenre_ids());
            tvSearchInfos.add(new ProgramSearchInfo(program.getId(),program.getTitle(),tvProgramInfo.getFirst_air_date(),firstGenreName,program.getPosterPath(),tvProgramInfo.getOverview()));
        });


        return new SearchTvResponseDto(tvSearchInfos,tvSearchList.getPage(),tvSearchList.getTotal_pages(),tvSearchList.getTotal_results());
    }



    //검색하는 open api 를 데이터로 받아오기
    private OpenApiSearchTrendingDto getSearchProgram(String type,String name,int page){
        OpenApiSearchTrendingDto openApiSearchTrendingDto=webClient.get()
                .uri("/search/"+type+"?query="+name+"&include_adult=false&language=ko&page="+page)
                .retrieve()
                .bodyToMono(OpenApiSearchTrendingDto.class)
                .block();
        return openApiSearchTrendingDto;
    }



}
