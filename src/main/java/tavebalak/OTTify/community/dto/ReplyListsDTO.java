package tavebalak.OTTify.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class ReplyListsDTO {
    private Long recommentId;
    private String content;
    private String nickName;
    private Long userId;
    private Timestamp createdDate;

    @Builder
    public ReplyListsDTO(Long recommentId, String content, String nickName, Long userId, Timestamp createdDate) {
        this.recommentId = recommentId;
        this.content = content;
        this.nickName = nickName;
        this.userId = userId;
        this.createdDate = createdDate;
    }
}
