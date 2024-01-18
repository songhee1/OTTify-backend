package tavebalak.OTTify.community.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@ApiModel(value = "CommunitySubjectImageEditDTO(토론주제 수정 정보)", description = "토론주제 id, 토론제목, 토론내용을 가진 Domain Class")
public class CommunitySubjectImageEditDTO {

    @NotNull
    @ApiModelProperty(value = "토론 주제 id")
    private Long subjectId;
    @NotBlank(message = "토론 주제 제목이 비워져 있어서는 안됩니다.")
    @ApiModelProperty(value = "토론 주제")
    private String subjectName;
    @NotBlank(message = "토론 주제 내용이 비워져 있어서는 안됩니다.")
    @ApiModelProperty(value = "토론 내용")
    private String content;
    @ApiParam(value = "이미지", required = false)
    private MultipartFile image;
}
