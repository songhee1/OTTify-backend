package tavebalak.OTTify.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tavebalak.OTTify.common.ApiResponse;
import tavebalak.OTTify.community.dto.CommunitySubjectCreateDTO;
import tavebalak.OTTify.community.dto.CommunitySubjectEditDTO;
import tavebalak.OTTify.community.dto.CommunitySubjectsDTO;
import tavebalak.OTTify.community.entity.Community;
import tavebalak.OTTify.community.service.CommunityService;
import tavebalak.OTTify.exception.NotFoundException;

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

    @PutMapping("/subject")
    public ApiResponse modifySubject(@RequestBody CommunitySubjectEditDTO c) throws NotFoundException {
        communityService.modifySubject(c);
        return ApiResponse.success("성공적으로 토론주제를 수정하였습니다.");
    }

    @DeleteMapping("/subject/{subjectId}")
    public ApiResponse deleteSubject(@PathVariable Long subjectId) throws NotFoundException {
        communityService.deleteSubject(subjectId);
        return ApiResponse.success("성공적으로 토론주제를 삭제하였습니다.");
    }

    @GetMapping("/subject")
    public ApiResponse getSubjects(@PageableDefault(size =  10) Pageable pageable){
        CommunitySubjectsDTO page = communityService.findAllSubjects(pageable);
        return ApiResponse.success(page);
    }
}
