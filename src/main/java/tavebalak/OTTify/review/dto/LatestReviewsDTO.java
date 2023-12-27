package tavebalak.OTTify.review.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class LatestReviewsDTO {
    private Long reviewId;
    private String nickName;
    private String content;
    private String programTitle;
    private double userRating;
    private String profilePhoto;
    private int likeCount;

    @Builder
    public LatestReviewsDTO(Long reviewId, String nickName, String content, String programTitle, double userRating, String profilePhoto, int likeCount) {
        this.reviewId = reviewId;
        this.nickName = nickName;
        this.content = content;
        this.programTitle = programTitle;
        this.userRating = userRating;
        this.profilePhoto = profilePhoto;
        this.likeCount = likeCount;
    }
}
