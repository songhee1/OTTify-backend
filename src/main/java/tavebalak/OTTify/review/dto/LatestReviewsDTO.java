package tavebalak.OTTify.review.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LatestReviewsDTO {
    private Long reviewId;
    private String nickName;
    private String content;
    private String programTitle;
    private double userAverageRating;
    private String profilePhoto;

    @Builder
    public LatestReviewsDTO(Long reviewId, String nickName, String content, String programTitle, double userAverageRating, String profilePhoto) {
        this.reviewId = reviewId;
        this.nickName = nickName;
        this.content = content;
        this.programTitle = programTitle;
        this.userAverageRating = userAverageRating;
        this.profilePhoto = profilePhoto;
    }
}
