package tavebalak.OTTify.program.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tavebalak.OTTify.common.BaseResponse;
import tavebalak.OTTify.program.dto.RecommendProgramsDTO;
import tavebalak.OTTify.program.service.ProgramService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/main")
public class ProgramMainController {
    private final ProgramService programService;

    @GetMapping("/recommendProgram/{count}")
    public BaseResponse getRecommendProgram(@PathVariable int count){
        RecommendProgramsDTO recommendProgramsDTO = programService.getRecommendProgram(count);
        return BaseResponse.success(recommendProgramsDTO);
    }
}
