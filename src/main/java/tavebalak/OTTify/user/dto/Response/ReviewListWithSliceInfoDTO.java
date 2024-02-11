package tavebalak.OTTify.user.dto.Response;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import tavebalak.OTTify.review.dto.response.MyReviewDto;

@Getter
public class ReviewListWithSliceInfoDTO {

    private List<MyReviewDto> ReviewList = new ArrayList<>();
    private boolean isLastPage;

    public ReviewListWithSliceInfoDTO(List<MyReviewDto> reviewList, boolean isLastPage) {
        ReviewList = reviewList;
        this.isLastPage = isLastPage;
    }
}
