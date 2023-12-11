package tavebalak.OTTify.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tavebalak.OTTify.community.entity.Reply;

import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor
public class CommunityAriclesDTO {
    private String title;
    private String writer;
    private Long userId;
    private String content;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private int commentAmount;
    private List<CommentListsDTO> commentListsDTOList;

    @Builder
    public CommunityAriclesDTO(String title, String writer, Long userId, String content, Timestamp createdDate, Timestamp modifiedDate, int commentAmount, List<CommentListsDTO> commentListsDTOList) {
        this.title = title;
        this.writer = writer;
        this.userId = userId;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.commentAmount = commentAmount;
        this.commentListsDTOList = commentListsDTOList;
    }
}
