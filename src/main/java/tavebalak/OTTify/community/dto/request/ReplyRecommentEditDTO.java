package tavebalak.OTTify.community.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReplyRecommentEditDTO {

    @NotNull
    private Long subjectId;
    @NotNull
    private Long commentId;
    @NotNull
    private Long recommentId;
    @NotBlank(message = "대댓글 수정시 내용이 비워져있으면 안됩니다.")
    private String content;

    @Builder
    public ReplyRecommentEditDTO(Long subjectId, Long commentId, Long recommentId, String content) {
        this.subjectId = subjectId;
        this.commentId = commentId;
        this.recommentId = recommentId;
        this.content = content;
    }
}
