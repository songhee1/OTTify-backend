package tavebalak.OTTify.community.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import java.io.IOException;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tavebalak.OTTify.common.BaseResponse;
import tavebalak.OTTify.community.dto.request.CommunitySubjectCreateDTO;
import tavebalak.OTTify.community.dto.request.CommunitySubjectEditDTO;
import tavebalak.OTTify.community.dto.request.ReplyCommentCreateDTO;
import tavebalak.OTTify.community.dto.request.ReplyCommentEditDTO;
import tavebalak.OTTify.community.dto.request.ReplyRecommentCreateDTO;
import tavebalak.OTTify.community.dto.request.ReplyRecommentEditDTO;
import tavebalak.OTTify.community.dto.response.CommunityAriclesDTO;
import tavebalak.OTTify.community.dto.response.CommunitySubjectsDTO;
import tavebalak.OTTify.community.service.CommunityService;
import tavebalak.OTTify.community.service.ReplyService;
import tavebalak.OTTify.exception.NotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/discussion")
@Api(tags = {"토론 컨트롤러"})

public class CommunityController {

    private final CommunityService communityService;
    private final ReplyService replyService;

    @ApiOperation(value = "토론주제 생성", notes = "회원이 작성한 토론내용을 기반으로 생성됩니다.")
    @ApiImplicitParam(name = "image", dataType = "MultipartFile", value = "토론글에 게시할 이미지", required = false, paramType = "query")
    @ApiResponse(code = 200, message = "성공적으로 토론주제를 생성하였습니다.")
    @PostMapping(value = "/subject", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
        MediaType.APPLICATION_JSON_VALUE})
    public BaseResponse<String> registerSubject(
        @Valid @RequestBody CommunitySubjectCreateDTO c,
        @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        communityService.saveSubject(image, c);
        return BaseResponse.success("성공적으로 토론주제를 생성하였습니다.");
    }

    @ApiOperation(value = "토론주제 수정", notes = "회원이 작성한 토론내용을 기반으로 수정됩니다.")
    @ApiResponse(code = 200, message = "성공적으로 토론주제를 수정하였습니다.")
    @PutMapping("/subject")
    public BaseResponse<String> modifySubject(@Valid @RequestBody CommunitySubjectEditDTO c)
        throws NotFoundException {
        communityService.modifySubject(c);
        return BaseResponse.success("성공적으로 토론주제를 수정하였습니다.");
    }

    @ApiOperation(value = "토론주제 삭제", notes = "회원이 작성한 토론주제를 삭제합니다.")
    @ApiImplicitParam(name = "subjectId", dataType = "long", value = "토론 주제 id", required = true, paramType = "path")
    @ApiResponse(code = 200, message = "성공적으로 토론주제를 삭제하였습니다.")
    @DeleteMapping("/subject/{subjectId}")
    public BaseResponse<String> deleteSubject(@PathVariable Long subjectId)
        throws NotFoundException {
        communityService.deleteSubject(subjectId);
        return BaseResponse.success("성공적으로 토론주제를 삭제하였습니다.");
    }

    @ApiOperation(value = "토론글 공감 및 해제", notes = "회원이 작성한 토론 주제에 대해 공감하고 해제합니다.")
    @ApiImplicitParam(name = "subjectId", dataType = "long", value = "토론 주제 id", required = true, paramType = "path")
    @ApiResponse(code = 200, message = "성공적으로 토론 게시글 공감 해제가 적용되었습니다.")
    @PostMapping("/like")
    public BaseResponse<String> likeSubject(@PathParam("subjectId") Long subjectId) {
        boolean hasLiked = communityService.likeSubject(subjectId);
        if (hasLiked) {
            return BaseResponse.success("성공적으로 토론 게시글 공감이 적용이 되었습니다.");
        }
        return BaseResponse.success("성공적으로 토론 게시글 공감 해제가 적용되었습니다.");
    }

    @ApiOperation(value = "토론댓글 공감 및 해제", notes = "회원이 작성한 토론 주제의 댓글에 대해 공감하고 해제합니다.")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "subjectId", dataType = "long", value = "토론글의 id", required = true, paramType = "path"),
        @ApiImplicitParam(name = "commentId", dataType = "long", value = "토론 댓글의 id", required = true, paramType = "path")
    })
    @ApiResponse(code = 200, message = "성공적으로 토론 댓글 공감이 적용/해제되었습니다.")
    @PostMapping("/like/comment")
    public BaseResponse<String> likeComment(@PathParam("subjectId") Long subjectId,
        @PathParam("commentId") Long commentId) {
        boolean hasLiked = communityService.likeComment(subjectId, commentId);
        if (hasLiked) {
            return BaseResponse.success("성공적으로 토론 댓글 공감 적용이 되었습니다.");
        }
        return BaseResponse.success("성공적으로 토론 댓글 공감 해제가 적용되었습니다.");
    }

    @ApiOperation(value = "토론 주제 조회", notes = "전체 프로그램에 대해 작성된 토론글을 조회합니다.")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "int", value = "페이지 번호(0부터 시작)", required = false, paramType = "path"),
        @ApiImplicitParam(name = "direction", dataType = "String", value = "내림차순과 오름차순", required = false, paramType = "path"),
        @ApiImplicitParam(name = "sort", dataType = "String", value = "정렬기준(createdAt, updatedAt)", required = false, paramType = "path")
    })
    @GetMapping("/total")
    public BaseResponse<CommunitySubjectsDTO> getTotalProgramsSubjects(@PageableDefault(size = 10,
        sort = "createdAt",
        direction = Sort.Direction.DESC,
        page = 0
    ) Pageable pageable) {
        CommunitySubjectsDTO page = communityService.findAllSubjects(pageable);
        return BaseResponse.success(page);
    }

    @ApiOperation(value = "프로그램별 토론주제 조회", notes = "특정 프로그램에 대해 작성된 토론글을 조회합니다.")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "int", value = "페이지 번호(0부터 시작)", required = false, paramType = "path"),
        @ApiImplicitParam(name = "direction", dataType = "String", value = "내림차순과 오름차순", required = false, paramType = "path"),
        @ApiImplicitParam(name = "sort", dataType = "String", value = "정렬기준(createdAt, updatedAt)", required = false, paramType = "path")
    })
    @GetMapping("/program")
    public BaseResponse<CommunitySubjectsDTO> getTotalProgramSubjects(@PageableDefault(size = 10,
        sort = "createdAt",
        direction = Sort.Direction.DESC,
        page = 0) Pageable pageable,
        @PathParam("programId") Long programId) {

        CommunitySubjectsDTO page = communityService.findSingleProgramSubjects(pageable, programId);
        return BaseResponse.success(page);
    }

    @ApiOperation(value = "토론 댓글 생성", notes = "회원이 작성한 토론 주제에 댓글을 생성한다.")
    @ApiResponse(code = 200, message = "성공적으로 토론 댓글을 생성하였습니다.")
    @PostMapping("/comment")
    public BaseResponse<String> registerComment(@Valid @RequestBody ReplyCommentCreateDTO c)
        throws NotFoundException {
        replyService.saveComment(c);
        return BaseResponse.success("성공적으로 토론댓글을 생성하였습니다.");
    }

    @ApiOperation(value = "토론 대댓글 생성", notes = "회원이 작성한 토론 주제의 댓글에 대댓글을 생성한다.")
    @ApiResponse(code = 200, message = "성공적으로 토론 대댓글을 생성하였습니다.")
    @PostMapping("/recomment")
    public BaseResponse<String> registerRecomment(@Valid @RequestBody ReplyRecommentCreateDTO c) {
        replyService.saveRecomment(c);
        return BaseResponse.success("성공적으로 토론 대댓글을 생성하였습니다.");
    }

    @ApiOperation(value = "토론 댓글 수정", notes = "회원이 작성한 토론 주제의 댓글을 수정한다.")
    @ApiResponse(code = 200, message = "성공적으로 토론 댓글을 수정하였습니다.")
    @PutMapping("/comment")
    public BaseResponse<String> modifyComment(@Valid @RequestBody ReplyCommentEditDTO c)
        throws NotFoundException {
        replyService.modifyComment(c);
        return BaseResponse.success("성공적으로 토론댓글을 수정하였습니다.");
    }

    @ApiOperation(value = "토론 대댓글 수정", notes = "회원이 작성한 토론 주제의 대댓글을 수정한다.")
    @ApiResponse(code = 200, message = "성공적으로 토론 대댓글을 수정하였습니다.")
    @PutMapping("/recomment")
    public BaseResponse<String> modifyRecomment(@Valid @RequestBody ReplyRecommentEditDTO c)
        throws NotFoundException {
        replyService.modifyRecomment(c);
        return BaseResponse.success("성공적으로 토론대댓글을 수정하였습니다.");
    }

    @ApiOperation(value = "토론댓글 삭제", notes = "회원이 작성한 토론 주제의 댓글을 삭제한다.")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "subjectId", dataType = "long", value = "토론글의 id", required = true, paramType = "path"),
        @ApiImplicitParam(name = "commentId", dataType = "long", value = "토론 대댓글의 id", required = true, paramType = "path")
    })
    @ApiResponse(code = 200, message = "성공적으로 토론 댓글을 삭제하였습니다.")
    @DeleteMapping("/comment/{subjectId}/{commentId}")
    public BaseResponse<String> deleteComment(@PathVariable Long subjectId,
        @PathVariable Long commentId) throws NotFoundException {
        replyService.deleteComment(subjectId, commentId);
        return BaseResponse.success("성공적으로 토론댓글을 삭제하였습니다.");
    }

    @ApiOperation(value = "토론 대댓글 삭제", notes = "회원이 작성한 토론 주제의 대댓글을 삭제한다.")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "subjectId", dataType = "long", value = "토론글의 id", required = true, paramType = "path"),
        @ApiImplicitParam(name = "commentId", dataType = "long", value = "토론 댓글의 id", required = true, paramType = "path"),
        @ApiImplicitParam(name = "recommentId", dataType = "long", value = "토론 대댓글의 id", required = true, paramType = "path")
    })
    @ApiResponse(code = 200, message = "성공적으로 토론 대댓글을 삭제하였습니다.")
    @DeleteMapping("/recomment/{subjectId}/{commentId}/{recommentId}")
    public BaseResponse<String> deleteRecomment(
        @PathVariable Long subjectId,
        @PathVariable Long commentId,
        @PathVariable Long recommentId) throws NotFoundException {
        replyService.deleteRecomment(subjectId, commentId, recommentId);
        return BaseResponse.success("성공적으로 토론대댓글을 삭제하였습니다.");
    }

    @ApiOperation(value = "토론 게시글 조회", notes = "작성된 토론 게시글 하나를 조회한다.")
    @ApiImplicitParam(name = "subjectId", dataType = "long", value = "토론글의 id", required = true, paramType = "path")
    @ApiResponse(code = 200, message = "성공적으로 토론 대댓글을 삭제하였습니다.")
    @GetMapping("/{subjectId}")
    public BaseResponse<CommunityAriclesDTO> getArticle(@PathVariable Long subjectId)
        throws NotFoundException {
        return BaseResponse.success(communityService.getArticleOfASubject(subjectId));
    }
}
