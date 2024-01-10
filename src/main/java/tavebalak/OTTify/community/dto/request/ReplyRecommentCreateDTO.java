package tavebalak.OTTify.community.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class ReplyRecommentCreateDTO {
    @NotNull
    private Long subjectId;
    @NotNull
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
