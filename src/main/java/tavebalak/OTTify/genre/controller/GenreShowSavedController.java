package tavebalak.OTTify.genre.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tavebalak.OTTify.common.BaseResponse;
import tavebalak.OTTify.genre.dto.response.GenreShowSavedListDto;
import tavebalak.OTTify.genre.service.GenreShowSavedService;

@RestController
@RequiredArgsConstructor
@Api(tags = {"저장된 장르를 보여주는 컨트롤러"})

public class GenreShowSavedController {

    private final GenreShowSavedService genreShowSavedService;

    @ApiOperation(value = "저장된 장르 리스트들을 보여줍니다", notes = "장르 조회 시 사용하시면 됩니다.")
    @GetMapping("/api/v1/show/saved/genre")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<GenreShowSavedListDto> showGenreSavedList() {
        return BaseResponse.success(genreShowSavedService.showGenreList());
    }
}
