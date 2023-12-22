package tavebalak.OTTify.program.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import tavebalak.OTTify.error.ErrorCode;
import tavebalak.OTTify.error.exception.NotFoundException;
import tavebalak.OTTify.genre.entity.Genre;
import tavebalak.OTTify.genre.repository.GenreRepository;
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


    // 검색을 통해 TV와 영화를 검색합니다.
    @Transactional
    public SearchResponseDto searchByName(String name){
        OpenApiSearchTrendingDto movieSearchList=getProgram("movie",name);
        SearchResponseDto searchResponseDto=new SearchResponseDto();
        movieSearchList.getResults().stream().map(ap->{
            Program program= apiProgramToProgram(ap,ProgramType.Movie);
            makeProgramResult(program,searchResponseDto,ap,ProgramType.Movie);
           return program;
        }).forEach(p->{
            if(!programRepository.existsByTmDbProgramId(p.getTmDbProgramId())){
                programRepository.save(p);
            }
        });

        OpenApiSearchTrendingDto tvSearchList=getProgram("tv",name);
        tvSearchList.getResults().stream().map(ap->{
            Program program= apiProgramToProgram(ap,ProgramType.TV);
            makeProgramResult(program,searchResponseDto,ap,ProgramType.TV);
            return program;
        }).forEach(p->{
            if(!programRepository.existsByTmDbProgramId(p.getTmDbProgramId())){
                programRepository.save(p);
            }
        });


     return searchResponseDto;
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

    //프로그램의 검색 결과를 보여주기 위해서 DTO를 만들고 ,연관관계 편의 메서드를 통해 프로그램에 장르를 넣어둡니다.
    private void makeProgramResult(Program program,SearchResponseDto searchResponseDto,OpenApiProgram openApiProgram,ProgramType programType){

        List<String> genreName=new ArrayList<>();
        openApiProgram.getGenre_ids().stream().forEach(gi->{
            Genre genre=genreRepository.findByTmDbGenreId(gi).orElseThrow(()->new NotFoundException(ErrorCode.ENTITY_NOT_FOUND));
            if(!programRepository.existsByTmDbProgramId(program.getTmDbProgramId())){
                program.addGenre(genre);
            }
            program.addGenre(genre);
            if(searchResponseDto.getMovieSearchInfos().size()<5 || searchResponseDto.getTvSearchInfos().size()<5){
                genreName.add(genre.getName());
            }

        });

        if(searchResponseDto.getMovieSearchInfos().size()<5 && programType==ProgramType.Movie){
            searchResponseDto.getMovieSearchInfos().add(new ProgramSearchInfo(openApiProgram.getTitle(),openApiProgram.getPoster_path(),genreName));
        }
        if(searchResponseDto.getTvSearchInfos().size()<5 && programType==ProgramType.TV){
            searchResponseDto.getTvSearchInfos().add(new ProgramSearchInfo(openApiProgram.getName(),openApiProgram.getPoster_path(),genreName));
        }
    }


}
