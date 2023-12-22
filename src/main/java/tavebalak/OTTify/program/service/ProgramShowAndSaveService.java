package tavebalak.OTTify.program.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import tavebalak.OTTify.error.ErrorCode;
import tavebalak.OTTify.error.exception.NotFoundException;
import tavebalak.OTTify.genre.entity.Genre;
import tavebalak.OTTify.genre.repository.GenreRepository;
import tavebalak.OTTify.genre.repository.ProgramGenreRepository;
import tavebalak.OTTify.program.dto.ProgramSearchInfo;
import tavebalak.OTTify.program.dto.OpenApiProgram;
import tavebalak.OTTify.program.dto.OpenApiSearchTrendingDto;
import tavebalak.OTTify.program.dto.SearchResponseDto;
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


    // 검색을 통해 TV와 영화를 검색합니다.
    @Transactional
    public SearchResponseDto searchByName(String name){

        SearchResponseDto searchResponseDto=new SearchResponseDto();

        OpenApiSearchTrendingDto movieSearchList=getProgram("movie",name);
        SearchAndSaveAndShow(movieSearchList,searchResponseDto,ProgramType.Movie);

        OpenApiSearchTrendingDto tvSearchList=getProgram("tv",name);
        SearchAndSaveAndShow(tvSearchList,searchResponseDto,ProgramType.TV);


        return searchResponseDto;
    }
     // 프로그램을 생성 후 저장되어 있지 않다면 저장, 및 사용자에게 반환할 SearchResponseDto 를 생성합니다.
    private void SearchAndSaveAndShow(OpenApiSearchTrendingDto openApiSearchTrendingDto,SearchResponseDto searchResponseDto,ProgramType programType){
        openApiSearchTrendingDto.getResults().stream().map(ap->{
            Program program;
            if(programRepository.existsByTmDbProgramIdAndAndType(ap.getId(),programType)){
                program=programRepository.findByTmDbProgramIdAndType(ap.getId(),programType);
            }
            else{
                program=apiProgramToProgram(ap,programType);
                ap.getGenre_ids().stream().forEach(l->{
                    Genre genre=genreRepository.findByTmDbGenreId(l).orElseThrow(()->new NotFoundException(ErrorCode.ENTITY_NOT_FOUND));
                    program.addGenre(genre);
                });
            }
            return program;
        }).forEach(p->{
            if(!programRepository.existsByTmDbProgramIdAndAndType(p.getTmDbProgramId(),programType)){
                System.out.println("안녕하세요");
                programRepository.save(p);
            }
            if(programType==ProgramType.Movie) {
                if(searchResponseDto.getMovieSearchInfos().size() < 5) {
                   makeSearchResponse(searchResponseDto,p,programType);
                }
            }
            if(programType==ProgramType.TV){
                if(searchResponseDto.getTvSearchInfos().size() < 5) {
                   makeSearchResponse(searchResponseDto,p,programType);
                }
            }
        });
    }


    //open api 를 통해 영화 목록 혹은 tv 목록을 가지고 옵니다
    private OpenApiSearchTrendingDto getProgram(String type,String name){
        OpenApiSearchTrendingDto openApiSearchTrendingDto=webClient.get()
                .uri("/search/"+type+"?query="+name+"&include_adult=false&language=ko&page="+1)
                .retrieve()
                .bodyToMono(OpenApiSearchTrendingDto.class)
                .block();
        return openApiSearchTrendingDto;
    }


    //받아온 api 정보를 이용해 우리가 만든 Program 에 필요한 값을 넣어줍니다.
    private Program apiProgramToProgram(OpenApiProgram openApiProgram,ProgramType programType){
        Program.ProgramBuilder programBuilder = Program.builder().tmDbProgramId(openApiProgram.getId())
                .type(programType)
                .posterPath(openApiProgram.getPoster_path());
        if(programType==ProgramType.Movie){
            programBuilder.title(openApiProgram.getTitle());
        }
        else if(programType==ProgramType.TV){
            programBuilder.title(openApiProgram.getName());
        }
        return programBuilder.build();
    }

    //프론트에 반환할 SearchResponseDto 를 작성합니다.

    private void makeSearchResponse(SearchResponseDto searchResponseDto,Program p,ProgramType programType){
        List<String> genreName = new ArrayList<>();
        programGenreRepository.findByProgram(p.getId()).stream().forEach(programGenre -> {
            genreName.add(programGenre.getGenre().getName());
        });
        if(programType==ProgramType.Movie) {
            searchResponseDto.getMovieSearchInfos().add(new ProgramSearchInfo(p.getId(), p.getTitle(), p.getPosterPath(), genreName));
        }
        if(programType==ProgramType.TV){
            searchResponseDto.getTvSearchInfos().add(new ProgramSearchInfo(p.getId(),p.getTitle(),p.getPosterPath(),genreName));
        }
    }

}
