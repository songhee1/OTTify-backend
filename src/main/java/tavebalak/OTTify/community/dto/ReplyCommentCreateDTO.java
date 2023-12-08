package tavebalak.OTTify.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReplyCommentCreateDTO {
    private Long subjectId;
    private String comment;

    @Builder
    public ReplyCommentCreateDTO(Long subjectId, String comment) {
        this.subjectId = subjectId;
        this.comment = comment;
    }
}
