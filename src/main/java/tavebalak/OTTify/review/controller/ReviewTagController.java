package tavebalak.OTTify.review.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tavebalak.OTTify.common.BaseResponse;
import tavebalak.OTTify.review.dto.reviewtagRequest.ReviewSaveTagDto;
import tavebalak.OTTify.review.dto.reviewtagResponse.ReviewTagListResponseDto;
import tavebalak.OTTify.review.service.ReviewTagService;

@RestController
@RequiredArgsConstructor
@Api(tags = {"리뷰 태그 컨트롤러"})

public class ReviewTagController {

    private final ReviewTagService reviewTagService;

    @ApiOperation(value = "리뷰 태그 저장", notes = "추후에 리뷰 태그를 따로 저장하는 로직이 필요할 때를 위해 남겨두었습니다")
    @ApiResponse(code = 200, message = "리뷰 태그 저장 완료")
    @PostMapping("/api/v1/reviewTag")
    public BaseResponse saveReviewTag(@RequestBody @Valid ReviewSaveTagDto reviewSaveTagDto) {
        reviewTagService.saveReviewTag(reviewSaveTagDto);
        return BaseResponse.success("리뷰 태그 저장 완료 ");
    }

    @ApiOperation(value = "저장된 리뷰 태그들을 보여줍니다", notes = "리뷰 태그 리스트를 보여줄 때 사용합니다")
    @GetMapping("/api/v1/reviewTag/list")
    public BaseResponse<ReviewTagListResponseDto> showReviewTags() {
        return BaseResponse.success(reviewTagService.showReviewTagList());
    }
}