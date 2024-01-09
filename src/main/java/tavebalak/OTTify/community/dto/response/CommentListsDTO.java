package tavebalak.OTTify.community.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class CommentListsDTO {
    private Long commentId;
    private String content;
    private String nickName;
    private LocalDateTime createdAt;
    private Long userId;
    private int likeCount;
    private List<ReplyListsDTO> replyListsDTOList;

    @Builder
    public CommentListsDTO(String content,
                           String nickName,
                           LocalDateTime createdAt,
                           Long userId,
                           List<ReplyListsDTO> replyListsDTOList,
                           int likeCount,
                           Long commentId
    ) {
        this.content = content;
        this.nickName = nickName;
        this.createdAt = createdAt;
        this.userId = userId;
        this.replyListsDTOList = replyListsDTOList;
        this.likeCount = likeCount;
        this.commentId = commentId;
    }
}
