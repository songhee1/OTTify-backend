package tavebalak.OTTify.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class ReplyCommentEditDTO {
    private Long subjectId;
    private Long commentId;
    @NotBlank(message = "댓글 내용이 비워져 있어서는 안됩니다.")
    private String comment;

    @Builder
    public ReplyCommentEditDTO(Long subjectId, Long commentId, String comment){
        this.subjectId = subjectId;
        this.commentId = commentId;
        this.comment = comment;
    }
}
