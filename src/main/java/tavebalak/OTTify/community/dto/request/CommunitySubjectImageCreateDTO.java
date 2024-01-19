package tavebalak.OTTify.community.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@AllArgsConstructor
@ApiModel(value = "CommunitySubjectImageCreateDTO(토론주제 생성 정보)", description = "토론주제 id, 토론제목, 토론내용, 이미지 파일 내용을 가진 Domain Class")
public class CommunitySubjectImageCreateDTO {

    @NotNull
    @ApiParam(value = "프로그램 id", required = true)
    private Long programId;

    @NotBlank(message = "토론 주제 제목이 비워져 있어서는 안됩니다.")
    @ApiParam(value = "토론 제목", required = true)
    private String subjectName;

    @NotBlank(message = "토론 내용이 비워져 있어서는 안됩니다.")
    @ApiParam(value = "토론 내용", required = true)
    private String content;
    
    @ApiParam(value = "이미지")
    private MultipartFile image;

}
