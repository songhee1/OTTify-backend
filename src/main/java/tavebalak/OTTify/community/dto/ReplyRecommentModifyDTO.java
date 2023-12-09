package tavebalak.OTTify.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReplyRecommentModifyDTO {
    private Long subjectId;
    private Long commentId;
    private Long recommentId;
    private String content;

    @Builder
    public ReplyRecommentModifyDTO(Long subjectId, Long commentId, Long recommentId, String content) {
        this.subjectId = subjectId;
        this.commentId = commentId;
        this.recommentId = recommentId;
        this.content = content;
    }
}
