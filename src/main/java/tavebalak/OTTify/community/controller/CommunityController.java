package tavebalak.OTTify.community.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tavebalak.OTTify.common.BaseResponse;
import tavebalak.OTTify.community.dto.response.CommunityAriclesDTO;
import tavebalak.OTTify.community.dto.request.*;
import tavebalak.OTTify.community.dto.response.CommunitySubjectsDTO;
import tavebalak.OTTify.community.service.CommunityService;
import tavebalak.OTTify.community.service.ReplyService;
import tavebalak.OTTify.exception.NotFoundException;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/discussion")
@Api(tags = {"토론 컨트롤러"})

public class CommunityController {
    private final CommunityService communityService;
    private final ReplyService replyService;

    @ApiOperation(value = "토론주제 생성", notes = "회원이 작성한 토론내용을 기반으로 생성됩니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "programId", dataType = "long", value = "토론하고 싶은 특정 프로그램 id"),
            @ApiImplicitParam(name = "subjectName", dataType = "String", value = "토론 제목"),
            @ApiImplicitParam(name = "content", dataType = "String", value = "토론 내용"),
            @ApiImplicitParam(name = "programTitle", dataType = "String", value = "프로그램명"),
            @ApiImplicitParam(name = "posterPath", dataType = "String", value = "프로그램 포스터 url")
    })
    @PostMapping(value = "/subject", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public BaseResponse<String> registerSubject(
            @Valid @RequestBody CommunitySubjectCreateDTO c,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        communityService.saveSubject(image, c);
        return BaseResponse.success("성공적으로 토론주제를 생성하였습니다.");
    }

    @PutMapping("/subject")
    public BaseResponse<String> modifySubject(@RequestBody CommunitySubjectEditDTO c) throws NotFoundException {
        communityService.modifySubject(c);
        return BaseResponse.success("성공적으로 토론주제를 수정하였습니다.");
    }

    @DeleteMapping("/subject/{subjectId}")
    public BaseResponse<String> deleteSubject(@PathVariable Long subjectId) throws NotFoundException {
        communityService.deleteSubject(subjectId);
        return BaseResponse.success("성공적으로 토론주제를 삭제하였습니다.");
    }

    @PostMapping("/like")
    public BaseResponse<String> likeSubject(@PathParam("subjectId") Long subjectId){
        boolean hasLiked = communityService.likeSubject(subjectId);
        if(hasLiked) return BaseResponse.success("성공적으로 토론 게시글 공감이 적용이 되었습니다.");
        return BaseResponse.success("성공적으로 토론 게시글 공감 해제가 적용되었습니다.");
    }

    @PostMapping("/like/comment")
    public BaseResponse<String> likeComment(@PathParam("subjectId") Long subjectId,
                                    @PathParam("commentId") Long commentId){
        boolean hasLiked = communityService.likeComment(subjectId, commentId);
        if(hasLiked) return BaseResponse.success("성공적으로 토론 댓글 공감 적용이 되었습니다.");
        return BaseResponse.success("성공적으로 토론 댓글 공감 해제가 적용되었습니다.");
    }

    @GetMapping("/total")
    public BaseResponse<CommunitySubjectsDTO> getTotalProgramsSubjects(@PageableDefault(size =  10,
                                                                  sort = "createdAt",
                                                                  direction = Sort.Direction.DESC,
                                                                  page = 0
    ) Pageable pageable){
        CommunitySubjectsDTO page = communityService.findAllSubjects(pageable);
        return BaseResponse.success(page);
    }

    @GetMapping("/program")
    public BaseResponse<CommunitySubjectsDTO> getTotalProgramSubjects(@PageableDefault(size = 10,
                                                                 sort = "createdAt",
                                                                 direction = Sort.Direction.DESC,
                                                                 page = 0) Pageable pageable,
                                               @PathParam("programId") Long programId){

        CommunitySubjectsDTO page = communityService.findSingleProgramSubjects(pageable, programId);
        return BaseResponse.success(page);
    }

    @PostMapping("/comment")
    public BaseResponse<String> registerComment(@Valid @RequestBody ReplyCommentCreateDTO c) throws NotFoundException {
        replyService.saveComment(c);
        return BaseResponse.success("성공적으로 토론댓글을 생성하였습니다.");
    }

    @PostMapping("/recomment")
    public BaseResponse<String> registerRecomment(@Valid @RequestBody ReplyRecommentCreateDTO c){
        replyService.saveRecomment(c);
        return BaseResponse.success("성공적으로 토론대댓글을 생성하였습니다.");
    }

    @PutMapping("/comment")
    public BaseResponse<String> modifyComment(@Valid @RequestBody ReplyCommentEditDTO c) throws NotFoundException {
        replyService.modifyComment(c);
        return BaseResponse.success("성공적으로 토론댓글을 수정하였습니다.");
    }

    @PutMapping("/recomment")
    public BaseResponse<String> modifyRecomment(@Valid @RequestBody ReplyRecommentEditDTO c) throws NotFoundException {
        replyService.modifyRecomment(c);
        return BaseResponse.success("성공적으로 토론대댓글을 수정하였습니다.");
    }

    @DeleteMapping("/comment/{subjectId}/{commentId}")
    public BaseResponse<String> deleteComment(@PathVariable Long subjectId,
                                      @PathVariable Long commentId) throws NotFoundException {
        replyService.deleteComment(subjectId, commentId);
        return BaseResponse.success("성공적으로 토론댓글을 삭제하였습니다.");
    }

    @DeleteMapping("/recomment/{subjectId}/{commentId}/{recommentId}")
    public BaseResponse<String> deleteRecomment(@PathVariable Long subjectId,
                                        @PathVariable Long commentId,
                                        @PathVariable Long recommentId) throws NotFoundException {
        replyService.deleteRecomment(subjectId, commentId, recommentId);
        return BaseResponse.success("성공적으로 토론대댓글을 삭제하였습니다.");
    }

    @GetMapping("/{subjectId}")
    public BaseResponse<CommunityAriclesDTO> getArticle(@PathVariable Long subjectId) throws NotFoundException {
        return BaseResponse.success(communityService.getArticleOfASubject(subjectId));
    }
}
