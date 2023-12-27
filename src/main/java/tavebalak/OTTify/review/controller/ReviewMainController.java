package tavebalak.OTTify.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tavebalak.OTTify.common.BaseResponse;
import tavebalak.OTTify.review.dto.LatestReviewsDTO;
import tavebalak.OTTify.review.service.ReviewService;

import javax.websocket.server.PathParam;
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

    @PostMapping("/latestReviews/like")
    public BaseResponse likeReview(@PathParam("id") Long id){
        reviewService.likeReview(id);
        return BaseResponse.success(id+"아이디 값을 가진 리뷰에 공감이 적용되었습니다.");
    }

    @PostMapping("/latestReviews/unlike")
    public BaseResponse unlikeReview(@PathParam("id") Long id){
        reviewService.unlikeReview(id);
        return BaseResponse.success(id+"아이디 값을 가진 리뷰에 공감이 해제되었습니다.");
    }

}
