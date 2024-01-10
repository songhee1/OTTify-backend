package tavebalak.OTTify.program.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tavebalak.OTTify.common.BaseResponse;
import tavebalak.OTTify.program.dto.response.RecommendProgramsDTO;
import tavebalak.OTTify.program.service.ProgramService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/main")
public class ProgramMainController {

    private final ProgramService programService;

    @ApiOperation(value = "추천 프로그램", notes = "회원의 찜 리스트와 프로그램 별점, 1순위, 2순위 장르에 따라 프로그램을 추천한다.")
    @GetMapping("/recommendProgram")
    public BaseResponse<RecommendProgramsDTO> getRecommendProgram() {
        RecommendProgramsDTO recommendProgramsDTO = programService.getRecommendProgram(6);
        return BaseResponse.success(recommendProgramsDTO);
    }
}
