package tavebalak.OTTify.review.dto;

import lombok.Builder;
import lombok.Getter;
import tavebalak.OTTify.review.entity.ReviewTag;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MyReviewDto {
    private String userProfilePhoto;
    private String userNickName;
    private String createdDate;
    private String programTitle;
    private Double rating;
    private List<ReviewTag> reviewTags = new ArrayList<>();
    private String content;
    private Integer likeCnt;

    @Builder
    public MyReviewDto(String userProfilePhoto, String userNickName, String createdDate, String programTitle, Double rating, List<ReviewTag> reviewTags, String content, Integer likeCnt) {
        this.userProfilePhoto = userProfilePhoto;
        this.userNickName = userNickName;
        this.createdDate = createdDate;
        this.programTitle = programTitle;
        this.rating = rating;
        this.reviewTags = reviewTags;
        this.content = content;
        this.likeCnt = likeCnt;
    }
}
