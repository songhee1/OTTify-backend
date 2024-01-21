package tavebalak.OTTify.review.dto.reviewresponse;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class ReviewListWithSliceInfoDto {

    private List<ReviewProgramResponseDto> reviewProgramResponseDtoList = new ArrayList<>();
    private boolean hasNextPage;


    public ReviewListWithSliceInfoDto(List<ReviewProgramResponseDto> reviewProgramResponseDtoList,
        boolean hasNextPage) {
        this.reviewProgramResponseDtoList = reviewProgramResponseDtoList;
        this.hasNextPage = hasNextPage;
    }
}
