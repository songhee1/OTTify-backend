package tavebalak.OTTify.review.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserReviewRatingListDTO {

    @ApiModelProperty(value = "총 리뷰 수")
    private int totalCnt;

    @ApiModelProperty(value = "0.5점 리뷰 수")
    private int pointFiveCnt;

    @ApiModelProperty(value = "1점 리뷰 수")
    private int oneCnt;

    @ApiModelProperty(value = "1.5점 리뷰 수")
    private int oneDotFiveCnt;

    @ApiModelProperty(value = "2점 리뷰 수")
    private int twoCnt;

    @ApiModelProperty(value = "2.5점 리뷰 수")
    private int twoDotFiveCnt;

    @ApiModelProperty(value = "3점 리뷰 수")
    private int threeCnt;

    @ApiModelProperty(value = "3.5점 리뷰 수")
    private int threeDotFiveCnt;

    @ApiModelProperty(value = "4점 리뷰 수")
    private int fourCnt;

    @ApiModelProperty(value = "4.5점 리뷰 수")
    private int fourDotFiveCnt;

    @ApiModelProperty(value = "5점 리뷰 수")
    private int fiveCnt;

    @Builder
    public UserReviewRatingListDTO(int totalCnt, int pointFiveCnt, int oneCnt, int oneDotFiveCnt, int twoCnt, int twoDotFiveCnt, int threeCnt, int threeDotFiveCnt, int fourCnt, int fourDotFiveCnt, int fiveCnt) {
        this.totalCnt = totalCnt;
        this.pointFiveCnt = pointFiveCnt;
        this.oneCnt = oneCnt;
        this.oneDotFiveCnt = oneDotFiveCnt;
        this.twoCnt = twoCnt;
        this.twoDotFiveCnt = twoDotFiveCnt;
        this.threeCnt = threeCnt;
        this.threeDotFiveCnt = threeDotFiveCnt;
        this.fourCnt = fourCnt;
        this.fourDotFiveCnt = fourDotFiveCnt;
        this.fiveCnt = fiveCnt;
    }
}
