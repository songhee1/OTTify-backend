package tavebalak.OTTify.program.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tavebalak.OTTify.common.BaseResponse;
import tavebalak.OTTify.program.dto.searchTrending.Response.*;
import tavebalak.OTTify.program.service.ProgramShowAndSaveService;


@RestController
@RequiredArgsConstructor
public class ProgramController {
    private final ProgramShowAndSaveService programShowAndSaveService;

    @GetMapping("/api/v1/program/trending")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<TrendingResponseDto> getTrendingProgram(){
        return BaseResponse.success(programShowAndSaveService.showTrending());
    }

    @GetMapping("/api/v1/program/search")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<SearchResponseDto> getSearchProgram(@RequestParam("name") String name){
        return BaseResponse.success(programShowAndSaveService.searchByName(name));
    }

    @GetMapping("/api/v1/movie/search")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<SearchMovieResponseDto> getSearchMovieProgram(@RequestParam("name") String name, @RequestParam("page") int page){
        return BaseResponse.success(programShowAndSaveService.searchByMovieName(name,page));
    }

    @GetMapping("/api/v1/tv/search")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<SearchTvResponseDto> getSearchTvProgram(@RequestParam("name") String name, @RequestParam("page") int page){
        return BaseResponse.success(programShowAndSaveService.searchByTvName(name,page));
    }



}
