package tavebalak.OTTify.program.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tavebalak.OTTify.common.BaseResponse;
import tavebalak.OTTify.program.dto.programDetails.Response.ProgramResponseDto;
import tavebalak.OTTify.program.service.ProgramDetailsShowService;

@RestController
@RequiredArgsConstructor
public class ProgramDetailsController {
    private final ProgramDetailsShowService programDetailsShowService;


    @GetMapping("/api/v1/program/{programId}/details")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ProgramResponseDto> getProgramDetails(@PathVariable("programId") Long programId){
        return BaseResponse.success(programDetailsShowService.showDetails(programId));
    }
}
