package tavebalak.OTTify.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class CommentListsDTO {
    private String content;
    private String nickName;
    private LocalDateTime createdDate;
    private Long userId;
    private List<ReplyListsDTO> replyListsDTOList;

    @Builder
    public CommentListsDTO(String content, String nickName, LocalDateTime createdDate, Long userId, List<ReplyListsDTO> replyListsDTOList) {
        this.content = content;
        this.nickName = nickName;
        this.createdDate = createdDate;
        this.userId = userId;
        this.replyListsDTOList = replyListsDTOList;
    }
}
