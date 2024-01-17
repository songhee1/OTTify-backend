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
@ApiModel(value = "CommunitySubjectEditDTO(토론주제 수정 정보)", description = "토론주제 id, 토론제목, 토론내용을 가진 Domain Class")
public class CommunitySubjectEditDTO {

    @NotNull
    @ApiModelProperty(value = "토론 주제 id")
    private Long subjectId;
    @NotBlank(message = "토론 주제 제목이 비워져 있어서는 안됩니다.")
    @ApiModelProperty(value = "토론 주제")
    private String subjectName;
    @NotBlank(message = "토론 주제 내용이 비워져 있어서는 안됩니다.")
    @ApiModelProperty(value = "토론 내용")
    private String content;

    @Builder
    public CommunitySubjectEditDTO(Long subjectId, String subjectName, String content) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.content = content;
    }
}
