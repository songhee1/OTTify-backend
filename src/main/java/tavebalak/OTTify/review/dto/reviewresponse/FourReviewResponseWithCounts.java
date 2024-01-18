package tavebalak.OTTify.review.dto.reviewresponse;

import lombok.Getter;

@Getter
public class FourReviewResponseWithCounts {

    private ReviewResponseDtoList reviewResponseDtoList;
    private int leftReviewCounts;

    public FourReviewResponseWithCounts(ReviewResponseDtoList reviewResponseDtoList,
        int leftReviewCounts) {
        this.reviewResponseDtoList = reviewResponseDtoList;
        this.leftReviewCounts = leftReviewCounts;
    }
}
