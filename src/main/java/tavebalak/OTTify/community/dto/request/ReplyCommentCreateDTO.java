package tavebalak.OTTify.community.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ApiModel(value = "ReplyCommentCreateDTO(댓글 생성 정보)", description = "토론주제 id, 토론댓글 내용을 가진 Domain Class")
public class ReplyCommentCreateDTO {

    @NotNull
    @ApiModelProperty(value = "토론 주제 id")
    private Long subjectId;
    @NotBlank(message = "댓글 내용은 비어있어서는 안됩니다.")
    @ApiModelProperty(value = "댓글 내용")
    private String comment;

    @Builder
    public ReplyCommentCreateDTO(Long subjectId, String comment) {
        this.subjectId = subjectId;
        this.comment = comment;
    }
}
