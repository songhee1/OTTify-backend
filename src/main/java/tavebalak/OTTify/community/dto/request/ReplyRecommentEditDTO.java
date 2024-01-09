package tavebalak.OTTify.community.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReplyRecommentEditDTO {
    private Long subjectId;
    private Long commentId;
    private Long recommentId;
    private String content;

    @Builder
    public ReplyRecommentEditDTO(Long subjectId, Long commentId, Long recommentId, String content) {
        this.subjectId = subjectId;
        this.commentId = commentId;
        this.recommentId = recommentId;
        this.content = content;
    }
}
