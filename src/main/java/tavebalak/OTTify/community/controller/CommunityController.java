package tavebalak.OTTify.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import tavebalak.OTTify.common.ApiResponse;
import tavebalak.OTTify.community.dto.*;
import tavebalak.OTTify.community.service.CommunityService;
import tavebalak.OTTify.community.service.ReplyService;
import tavebalak.OTTify.exception.NotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/discussion")
public class CommunityController {
    private final CommunityService communityService;
    private final ReplyService replyService;

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

    @PostMapping("/comment")
    public ApiResponse registerComment(@RequestBody ReplyCommentCreateDTO c){
        replyService.saveComment(c);
        return ApiResponse.success("성공적으로 토론댓글을 생성하였습니다.");
    }

    @PostMapping("/recomment")
    public ApiResponse registerRecomment(@RequestBody ReplyRecommentCreateDTO c){
        replyService.saveRecomment(c);
        return ApiResponse.success("성공적으로 토론대댓글을 생성하였습니다.");
    }
}