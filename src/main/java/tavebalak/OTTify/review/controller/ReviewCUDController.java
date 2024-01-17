package tavebalak.OTTify.review.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tavebalak.OTTify.common.BaseResponse;
import tavebalak.OTTify.error.ErrorCode;
import tavebalak.OTTify.error.exception.UnauthorizedException;
import tavebalak.OTTify.oauth.jwt.SecurityUtil;
import tavebalak.OTTify.review.dto.reviewrequest.ReviewSaveDto;
import tavebalak.OTTify.review.dto.reviewrequest.ReviewUpdateDto;
import tavebalak.OTTify.review.service.ReviewCUDService;
import tavebalak.OTTify.user.entity.User;
import tavebalak.OTTify.user.repository.UserRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
@Api(tags = {"리뷰 저장 수정 삭제 및 리뷰 좋아요 컨트롤러"})

public class ReviewCUDController {

    private final ReviewCUDService reviewCUDService;
    private final UserRepository userRepository;

    @ApiOperation(value = "리뷰 저장", notes = "프로그램에 대한 리뷰를 저장합니다")
    @ApiResponse(code = 200, message = "리뷰 저장 완료")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse saveReview(@RequestBody @Valid ReviewSaveDto reviewSaveDto) {
        User findUser = getUser();
        reviewCUDService.saveReview(findUser, reviewSaveDto);
        return BaseResponse.success("리뷰 저장 완료");
    }

    @ApiOperation(value = "리뷰 수정", notes = "프로그램에 대한 리뷰를 수정합니다")
    @ApiResponse(code = 200, message = "리뷰 수정 완료")
    @ApiImplicitParam(name = "reviewId", dataType = "long", value = "리뷰 ID 값", required = true, paramType = "path", example = "1")
    @PutMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse updateReview(@PathVariable("reviewId") Long reviewId,
        @RequestBody @Valid ReviewUpdateDto reviewUpdateDto) {
        User findUser = getUser();
        reviewCUDService.updateReview(findUser, reviewId, reviewUpdateDto);
        return BaseResponse.success("리뷰 수정 완료");
    }

    @ApiOperation(value = "리뷰 삭제", notes = "프로그램에 대한 리뷰를 삭제합니다")
    @ApiResponse(code = 200, message = "리뷰 삭제 완료")
    @ApiImplicitParam(name = "reviewId", dataType = "long", value = "리뷰 ID 값", required = true, paramType = "path", example = "1")
    @DeleteMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse deleteReview(@PathVariable("reviewId") Long reviewId) {
        User findUser = getUser();
        reviewCUDService.deleteReview(findUser, reviewId);
        return BaseResponse.success("리뷰 삭제 완료");
    }

    @ApiOperation(value = "리뷰 좋아요 및 해제", notes = "프로그램에 대한 리뷰 좋아요 혹은 해제를 실행합니다")
    @ApiResponse(code = 200, message = "리뷰 좋아요 및 해제 완료")
    @ApiImplicitParam(name = "reviewId", dataType = "long", value = "리뷰 ID 값", required = true, paramType = "path", example = "1")
    @PostMapping("/like/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse likeReview(@PathVariable("reviewId") Long reviewId) {
        User findUser = getUser();
        reviewCUDService.likeReview(findUser, reviewId);
        return BaseResponse.success("리뷰 좋아요 및 해제 완료");
    }

    private User getUser() {
        return userRepository.findByEmail(SecurityUtil.getCurrentEmail().get())
            .orElseThrow(() -> new UnauthorizedException(ErrorCode.UNAUTHORIZED));
    }
}
