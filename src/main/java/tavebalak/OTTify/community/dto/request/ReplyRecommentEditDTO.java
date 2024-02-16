package tavebalak.OTTify.community.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "ReplyRecommentEditDTO(토론 대댓글 수정 정보)", description = "토론주제 id, 토론 댓글 id, 토론 대댓글 id, 토론 대댓글 내용을 가진 Domain Class")
public class ReplyRecommentEditDTO {

    @NotNull
    @ApiModelProperty(value = "토론 주제 id", required = true)
    private Long subjectId;

    @NotNull
    @ApiModelProperty(value = "토론 댓글 id", required = true)
    private Long commentId;

    @NotNull
    @ApiModelProperty(value = "토론 대댓글 id", required = true)
    private Long recommentId;

    @NotBlank(message = "대댓글 수정시 내용이 비워져있으면 안됩니다.")
    @ApiModelProperty(value = "토론 대댓글 내용", required = true)
    private String comment;

    @Builder
    public ReplyRecommentEditDTO(Long subjectId, Long commentId, Long recommentId, String comment) {
        this.subjectId = subjectId;
        this.commentId = commentId;
        this.recommentId = recommentId;
        this.comment = comment;
    }
}
