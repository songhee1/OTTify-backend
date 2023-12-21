package tavebalak.OTTify.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tavebalak.OTTify.common.ApiResponse;
import tavebalak.OTTify.common.BaseResponse;
import tavebalak.OTTify.review.dto.LatestReviewsDTO;
import tavebalak.OTTify.review.service.ReviewService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/main")
public class ReviewMainController {
    private final ReviewService reviewService;

    @GetMapping("/latestReviews")
    public BaseResponse getLatestReviews(){
        List<LatestReviewsDTO> reviewsDTOList = reviewService.getLatestReviews();
        return BaseResponse.success(reviewsDTOList);
    }
}
