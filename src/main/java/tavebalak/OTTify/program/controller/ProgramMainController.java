package tavebalak.OTTify.program.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tavebalak.OTTify.common.ApiResponse;
import tavebalak.OTTify.program.dto.RecommendProgramsDTO;
import tavebalak.OTTify.program.service.ProgramService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/main")
public class ProgramMainController {
    private final ProgramService programService;

    @GetMapping("/recommendProgram")
    public ApiResponse getRecommendProgram(){
        RecommendProgramsDTO recommendProgramsDTO = programService.getRecommendProgram();
        return ApiResponse.success(recommendProgramsDTO);
    }
}
