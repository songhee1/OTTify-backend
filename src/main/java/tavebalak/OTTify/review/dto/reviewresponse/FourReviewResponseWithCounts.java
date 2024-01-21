package tavebalak.OTTify.review.dto.reviewresponse;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class FourReviewResponseWithCounts {

    List<ReviewProgramResponseDto> reviewProgramResponseDtoList = new ArrayList<>();
    private int leftReviewCounts;

    public FourReviewResponseWithCounts(List<ReviewProgramResponseDto> reviewProgramResponseDtoList,
        int leftReviewCounts) {
        this.reviewProgramResponseDtoList = reviewProgramResponseDtoList;
        this.leftReviewCounts = leftReviewCounts;
    }
}
