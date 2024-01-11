package tavebalak.OTTify.community.dto.request;

import io.swagger.annotations.ApiParam;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReplyRecommentCreateDTO {

    @NotNull
    @ApiParam(name = "토론 주제 id", required = true)
    private Long subjectId;
    @NotNull
    @ApiParam(name = "토론 대댓글 id", required = true)
    private Long commentId;
    @NotBlank(message = "대댓글 내용이 비워져 있어서는 안됩니다.")
    @ApiParam(name = "토론 대댓글 내용", required = true)
    private String content;

    @Builder
    public ReplyRecommentCreateDTO(Long subjectId, Long commentId, String content) {
        this.subjectId = subjectId;
        this.commentId = commentId;
        this.content = content;
    }
}
