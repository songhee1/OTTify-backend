package tavebalak.OTTify.program.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tavebalak.OTTify.common.BaseResponse;
import tavebalak.OTTify.program.dto.response.OttListDTO;
import tavebalak.OTTify.program.service.OttService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ott")
@Api(tags = {"OTT 컨트롤러"})
public class OttController {

    private final OttService ottService;

    @ApiOperation(value = "저장된 OTT 리스트 조회", notes = "저장된 모든 OTT의 아이디와 이름을 조회합니다.")
    @ApiResponse(code = 200, message = "성공적으로 저장된 OTT 리스트를 조회하였습니다.")
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<OttListDTO> getOttList() {
        return BaseResponse.success(ottService.getOttList());
    }
}
