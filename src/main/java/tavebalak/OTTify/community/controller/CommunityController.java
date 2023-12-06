package tavebalak.OTTify.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tavebalak.OTTify.common.ApiResponse;
import tavebalak.OTTify.community.dto.CommunitySubjectCreateDTO;
import tavebalak.OTTify.community.service.CommunityService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/discussion")
public class CommunityController {
    private final CommunityService communityService;

    @PostMapping("/subject")
    public ApiResponse registerSubject(@RequestBody CommunitySubjectCreateDTO c){
        communityService.saveSubject(c);
        return ApiResponse.success("성공적으로 토론주제를 생성하였습니다.");
    }
}
