package tavebalak.OTTify.review.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MyReviewDto {

    private Long reviewId;
    private LocalDateTime createdDate;
    private String userProfilePhoto;
    private String userNickName;
    private String programTitle;
    private Double reviewRating;
    private List<String> reviewTagNames = new ArrayList<>();
    private String content;
    private int likeCnt;

    @Builder
    public MyReviewDto(Long reviewId, LocalDateTime createdDate, String userProfilePhoto, String userNickName, String programTitle, Double reviewRating, List<String> reviewTagNames, String content,
        int likeCnt) {
        this.reviewId = reviewId;
        this.createdDate = createdDate;
        this.userProfilePhoto = userProfilePhoto;
        this.userNickName = userNickName;
        this.programTitle = programTitle;
        this.reviewRating = reviewRating;
        this.reviewTagNames = reviewTagNames;
        this.content = content;
        this.likeCnt = likeCnt;
    }
}
