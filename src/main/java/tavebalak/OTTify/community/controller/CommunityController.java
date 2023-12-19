package tavebalak.OTTify.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tavebalak.OTTify.common.ApiResponse;
import tavebalak.OTTify.community.dto.*;
import tavebalak.OTTify.community.service.CommunityService;
import tavebalak.OTTify.community.service.ReplyService;
import tavebalak.OTTify.exception.NotFoundException;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

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

    @GetMapping("/total")
    public ApiResponse getTotalProgramsSubjects(@PageableDefault(size =  10) Pageable pageable){
        CommunitySubjectsDTO page = communityService.findAllSubjects(pageable);
        return ApiResponse.success(page);
    }

    @GetMapping("/program")
    public ApiResponse getTotalProgramSubjects(@PageableDefault(size = 10) Pageable pageable,
                                               @PathParam("programId") Long programId){
        CommunitySubjectsDTO page = communityService.findSingleProgramSubjects(pageable, programId);
        return ApiResponse.success(page);
    }

    @PostMapping("/comment")
    public ApiResponse registerComment(@Valid @RequestBody final ReplyCommentCreateDTO c) throws NotFoundException {
        replyService.saveComment(c);
        return ApiResponse.success("성공적으로 토론댓글을 생성하였습니다.");
    }

    @PostMapping("/recomment")
    public ApiResponse registerRecomment(@Valid @RequestBody final ReplyRecommentCreateDTO c){
        replyService.saveRecomment(c);
        return ApiResponse.success("성공적으로 토론대댓글을 생성하였습니다.");
    }

    @PutMapping("/comment")
    public ApiResponse modifyComment(@Valid @RequestBody final ReplyCommentEditDTO c) throws NotFoundException {
        replyService.modifyComment(c);
        return ApiResponse.success("성공적으로 토론댓글을 수정하였습니다.");
    }

    @PutMapping("/recomment")
    public ApiResponse modifyRecomment(@Valid @RequestBody ReplyRecommentEditDTO c) throws NotFoundException {
        replyService.modifyRecomment(c);
        return ApiResponse.success("성공적으로 토론대댓글을 수정하였습니다.");
    }

    @DeleteMapping("/comment/{subjectId}/{commentId}")
    public ApiResponse deleteComment(@PathVariable Long subjectId, @PathVariable Long commentId) throws NotFoundException {
        replyService.deleteComment(subjectId, commentId);
        return ApiResponse.success("성공적으로 토론댓글을 삭제하였습니다.");
    }

    @DeleteMapping("/comment/{subjectId}/{commentId}/{recommentId}")
    public ApiResponse deleteRecomment(@PathVariable Long subjectId, @PathVariable Long commentId, @PathVariable Long recommentId) throws NotFoundException {
        replyService.deleteRecomment(subjectId, commentId, recommentId);
        return ApiResponse.success("성공적으로 토론대댓글을 삭제하였습니다.");
    }

    @GetMapping("/{subjectId}")
    public ApiResponse getArticles(@PathVariable Long subjectId) throws NotFoundException {
        CommunityAriclesDTO communityAriclesDTOList = communityService.getArticles(subjectId);
        return ApiResponse.success(communityAriclesDTOList);
    }
}
