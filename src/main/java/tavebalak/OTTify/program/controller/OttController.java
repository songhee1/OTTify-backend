package tavebalak.OTTify.program.controller;

import io.swagger.annotations.Api;
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
public class OttController {

    private final OttService ottService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<OttListDTO> getOttList() {
        return BaseResponse.success(ottService.getOttList());
    }
}
