package tavebalak.OTTify.review.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tavebalak.OTTify.common.BaseResponse;
import tavebalak.OTTify.error.ErrorCode;
import tavebalak.OTTify.error.exception.UnauthorizedException;
import tavebalak.OTTify.oauth.jwt.SecurityUtil;
import tavebalak.OTTify.review.dto.reviewresponse.FourReviewResponseWithCounts;
import tavebalak.OTTify.review.dto.reviewresponse.ReviewListWithSliceInfoDto;
import tavebalak.OTTify.review.dto.reviewresponse.ReviewProgramResponseDto;
import tavebalak.OTTify.review.service.ReviewShowProgramDetailService;
import tavebalak.OTTify.user.entity.User;
import tavebalak.OTTify.user.repository.UserRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews/{programId}")
@Api(tags = {"프로그램 상세 페이지에서 리뷰 보여주는 컨트롤러"})

public class ReviewShowProgramDetailController {

    private final ReviewShowProgramDetailService reviewShowProgramDetailService;
    private final UserRepository userRepository;

    @ApiOperation(value = "프로그램 페이지에서 나의 리뷰 보여주기", notes = "내가 이 프로그램에 대해 리뷰를 작성한 경우 그것을 보여줍니다.")
    @ApiImplicitParam(name = "programId", dataType = "long", value = "현재 프로그램의 ID", required = true, paramType = "path")
    @GetMapping("/myReview")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ReviewProgramResponseDto> showMyReview(
        @PathVariable("programId") Long programId) {
        User findUser = getUser();
        return BaseResponse.success(
            reviewShowProgramDetailService.showMyReview(findUser, programId));
    }


    @ApiOperation(value = "프로그램 페이지의 초기 리뷰 보여주기", notes = "프로그램 페이지의 처음 4개의 리뷰를 보여줍니다")
    @ApiImplicitParam(name = "programId", dataType = "long", value = "현재 프로그램의 ID", required = true, paramType = "path")
    @GetMapping("/normal/count/4")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<FourReviewResponseWithCounts> show4ReviewList(
        @PathVariable("programId") Long programId) {
        return BaseResponse.success(reviewShowProgramDetailService.show4Review(programId));
    }

    @ApiOperation(value = "전체 리뷰 리스트를 보여주기", notes = "전체 리뷰 리스트를 보여줍니다")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "int", value = "페이지 번호(0부터 시작)", paramType = "query"),
        @ApiImplicitParam(name = "direction", dataType = "String", value = "내림차순과 오름차순", paramType = "query"),
        @ApiImplicitParam(name = "sort", dataType = "String", value = "정렬기준(likeCounts,createdAt)", paramType = "query"),
        @ApiImplicitParam(name = "size", dataType = "int", value = "페이지당 아이템 갯수", paramType = "query")
    })
    @GetMapping("/normals")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ReviewListWithSliceInfoDto> showReviewListALL(
        @PathVariable("programId") Long programId,
        @PageableDefault(size = 10,
            sort = "likeCounts",
            direction = Sort.Direction.DESC,
            page = 0) Pageable pageable) {
        return BaseResponse.success(
            reviewShowProgramDetailService.showReviewList(programId, pageable));
    }

    @ApiOperation(value = "프로그램 페이지의 자신의 취향에 맞는 리뷰 4개 보여주기", notes = "자신의 취향에 맞는 좋아요 순  처음 4개의 리뷰를 보여줍니다")
    @ApiImplicitParam(name = "programId", dataType = "long", value = "현재 프로그램의 ID", required = true, paramType = "path", example = "1")
    @GetMapping("/user/specific/count/4")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<FourReviewResponseWithCounts> show4UserSpecificReviewList(
        @PathVariable("programId") Long programId) {
        User findUser = getUser();
        return BaseResponse.success(
            reviewShowProgramDetailService.show4UserSpecificReviewList(findUser, programId));
    }


    @ApiOperation(value = "사용자의 취향에 맞는 전체 리뷰 리스트를 보여주기", notes = "자신의 취향에 맞는 리뷰 리스트를 보여줍니다")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "int", value = "페이지 번호(0부터 시작)", paramType = "query"),
        @ApiImplicitParam(name = "direction", dataType = "String", value = "내림차순과 오름차순", paramType = "query"),
        @ApiImplicitParam(name = "sort", dataType = "String", value = "정렬기준(likeCounts,createdAt)", paramType = "query"),
        @ApiImplicitParam(name = "size", dataType = "int", value = "페이지당 아이템 갯수", paramType = "query")
    })
    @GetMapping("/user/specifics")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ReviewListWithSliceInfoDto> showUserSpecificReviewListALL(
        @PathVariable("programId") Long programId,
        @PageableDefault(size = 10,
            sort = "likeCounts",
            direction = Sort.Direction.DESC,
            page = 0) Pageable pageable) {
        User findUser = getUser();
        return BaseResponse.success(
            reviewShowProgramDetailService.showUserSpecificReviewList(findUser, programId,
                pageable));
    }


    private User getUser() {
        return userRepository.findByEmail(SecurityUtil.getCurrentEmail().get())
            .orElseThrow(() -> new UnauthorizedException(ErrorCode.UNAUTHORIZED));
    }
}
