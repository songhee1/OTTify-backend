package tavebalak.OTTify.review.dto.response;

import lombok.Builder;
import lombok.Getter;
import tavebalak.OTTify.review.entity.ReviewTag;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MyReviewDto {
    private Long reviewId;
    private String createdDate;
    private String userProfilePhoto;
    private String userNickName;
    private String programTitle;
    private Double reviewRating;
    private List<ReviewTag> reviewTags = new ArrayList<>();
    private String content;
    private int likeCnt;

    @Builder
    public MyReviewDto(Long reviewId, String createdDate, String userProfilePhoto, String userNickName, String programTitle, Double reviewRating, List<ReviewTag> reviewTags, String content, int likeCnt) {
        this.reviewId = reviewId;
        this.createdDate = createdDate;
        this.userProfilePhoto = userProfilePhoto;
        this.userNickName = userNickName;
        this.programTitle = programTitle;
        this.reviewRating = reviewRating;
        this.reviewTags = reviewTags;
        this.content = content;
        this.likeCnt = likeCnt;
    }
}
