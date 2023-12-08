package tavebalak.OTTify.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReplyRecommentCreateDTO {
    private Long subjectId;
    private Long commentId;
    private String content;

    @Builder
    public ReplyRecommentCreateDTO(Long subjectId, Long commentId, String content) {
        this.subjectId = subjectId;
        this.commentId = commentId;
        this.content = content;
    }
}
