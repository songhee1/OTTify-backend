package tavebalak.OTTify.community.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MyDiscussionDto {
    private Long discussionId;
    private String createdDate;
    private String programTitle;
    private String discussionTitle;
    private String content;
    private String img;
    private Long likeCnt;
    private Long replyCnt;

    @Builder
    public MyDiscussionDto(Long discussionId, String createdDate, String programTitle, String discussionTitle, String content, String img, Long likeCnt, Long replyCnt) {
        this.discussionId = discussionId;
        this.createdDate = createdDate;
        this.programTitle = programTitle;
        this.discussionTitle = discussionTitle;
        this.content = content;
        this.img = img;
        this.likeCnt = likeCnt;
        this.replyCnt = replyCnt;
    }
}
