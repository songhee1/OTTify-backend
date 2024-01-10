package tavebalak.OTTify.review.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
@ApiModel(value = "LatestReviewsDTO(최신 리뷰 조회 정보)", description = "리뷰 id, 글쓴이, 리뷰글, 프로그램 제목, 글쓴이 평균 별점, 프로필 사진, 공감 수 내용을 가진 Domain Class")
public class LatestReviewsDTO {

    @ApiModelProperty(value = "리뷰 id")
    private Long reviewId;
    @ApiModelProperty(value = "글쓴이")
    private String nickName;
    @ApiModelProperty(value = "리뷰 글")
    private String content;
    @ApiModelProperty(value = "프로그램 제목")
    private String programTitle;
    @ApiModelProperty(value = "글쓴이 평균 별점")
    private double userRating;
    @ApiModelProperty(value = "프로필 사진")
    private String profilePhoto;
    @ApiModelProperty(value = "공감 수")
    private int likeCount;

    @Builder
    public LatestReviewsDTO(Long reviewId, String nickName, String content, String programTitle,
        double userRating, String profilePhoto, int likeCount) {
        this.reviewId = reviewId;
        this.nickName = nickName;
        this.content = content;
        this.programTitle = programTitle;
        this.userRating = userRating;
        this.profilePhoto = profilePhoto;
        this.likeCount = likeCount;
    }
}
