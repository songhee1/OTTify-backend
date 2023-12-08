package tavebalak.OTTify.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReplyCommentEditDTO {
    private Long subjectId;
    private Long commentId;
    private String comment;

    @Builder
    public ReplyCommentEditDTO(Long subjectId, Long commentId, String comment){
        this.subjectId = subjectId;
        this.commentId = commentId;
        this.comment = comment;
    }
}
