package tavebalak.OTTify.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class ReplyRecommentCreateDTO {
    @NotBlank(message = "주제ID 값이 비워져 있어서는 안됩니다.")
    private Long subjectId;
    @NotBlank(message = "댓글ID 값이 비워져 있어서는 안됩니다.")
    private Long commentId;
    @NotBlank(message = "대댓글 내용이 비워져 있어서는 안됩니다.")
    private String content;

    @Builder
    public ReplyRecommentCreateDTO(Long subjectId, Long commentId, String content) {
        this.subjectId = subjectId;
        this.commentId = commentId;
        this.content = content;
    }
}
