package tavebalak.OTTify.program.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tavebalak.OTTify.common.BaseResponse;
import tavebalak.OTTify.program.dto.TrendingResponseDto;
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



}
