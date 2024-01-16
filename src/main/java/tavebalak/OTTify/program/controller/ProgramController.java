package tavebalak.OTTify.program.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tavebalak.OTTify.common.BaseResponse;
import tavebalak.OTTify.program.dto.searchTrending.Response.SearchMovieResponseDto;
import tavebalak.OTTify.program.dto.searchTrending.Response.SearchResponseDto;
import tavebalak.OTTify.program.dto.searchTrending.Response.SearchTvResponseDto;
import tavebalak.OTTify.program.dto.searchTrending.Response.TrendingResponseDto;
import tavebalak.OTTify.program.service.ProgramShowAndSaveService;


@RestController
@RequiredArgsConstructor
@Api(tags = {"프로그램 트렌딩 및 검색 컨트롤러"})

public class ProgramController {

    private final ProgramShowAndSaveService programShowAndSaveService;

    @ApiOperation(value = "트렌딩 페이지 가져오기", notes = "주간 트렌딩과 일간 트렌딩을 OpenAPi를 통해서 가져옵니다.")
    @GetMapping("/api/v1/program/trending")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<TrendingResponseDto> getTrendingProgram() {
        return BaseResponse.success(programShowAndSaveService.showTrending());
    }

    @ApiOperation(value = "프로그램 검색하기", notes = "초기화면에서 프로그램을 검색합니다.")
    @ApiImplicitParam(name = "name", dataType = "String", value = "프로그램명 검색", required = true, paramType = "query", example = "코코")
    @GetMapping("/api/v1/program/search")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<SearchResponseDto> getSearchProgram(@RequestParam("name") String name) {
        return BaseResponse.success(programShowAndSaveService.searchByName(name));
    }

    @ApiOperation(value = "영화 검색하기", notes = "영화를 검색합니다")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "name", dataType = "String", value = "영화명 검색", required = true, paramType = "query", example = "코코"),
        @ApiImplicitParam(name = "page", dataType = "int", value = "페이지 번호(1부터 시작)", required = true, paramType = "query", example = "1")
    })
    @GetMapping("/api/v1/movie/search")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<SearchMovieResponseDto> getSearchMovieProgram(
        @RequestParam("name") String name, @RequestParam("page") int page) {
        return BaseResponse.success(programShowAndSaveService.searchByMovieName(name, page));
    }

    @ApiOperation(value = "TV 프로그램 검색하기", notes = "TV를 검색합니다")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "name", dataType = "String", value = "TV 프로그램 검색", required = true, paramType = "query", example = "나혼자산다"),
        @ApiImplicitParam(name = "page", dataType = "int", value = "페이지 번호(1부터 시작)", required = true, paramType = "query", example = "1")
    })
    @GetMapping("/api/v1/tv/search")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<SearchTvResponseDto> getSearchTvProgram(@RequestParam("name") String name,
        @RequestParam("page") int page) {
        return BaseResponse.success(programShowAndSaveService.searchByTvName(name, page));
    }


}
