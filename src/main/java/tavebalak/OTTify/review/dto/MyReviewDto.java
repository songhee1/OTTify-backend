package tavebalak.OTTify.review.dto;

import lombok.Builder;
import lombok.Getter;
import tavebalak.OTTify.review.entity.ReviewTag;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MyReviewDto {
    String userProfilePhoto;
    String userNickName;
    String createdDate;
    String programTitle;
    Double rating;
    List<ReviewTag> reviewTags = new ArrayList<>();
    String content;
    Integer likeCnt;

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
