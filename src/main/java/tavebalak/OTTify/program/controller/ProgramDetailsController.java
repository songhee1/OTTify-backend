package tavebalak.OTTify.program.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = {"프로그램에 대한 상세정보 컨트롤러"})

public class ProgramDetailsController {

    private final ProgramDetailsShowService programDetailsShowService;

    @ApiOperation(value = "프로그램에 대한 상세 정보 보기", notes = "프로그램에 대한 상세 정보를 받아옵니다")
    @ApiImplicitParam(name = "programId", dataType = "long", value = "프로그램 ID", required = true, paramType = "path")
    @GetMapping("/api/v1/program/{programId}/details")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ProgramResponseDto> getProgramDetails(
        @PathVariable("programId") Long programId) {
        return BaseResponse.success(programDetailsShowService.showDetails(programId));
    }
}
