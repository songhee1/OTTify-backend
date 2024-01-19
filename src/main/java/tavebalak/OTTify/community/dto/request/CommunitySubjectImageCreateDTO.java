package tavebalak.OTTify.community.dto.request;

import io.swagger.annotations.ApiParam;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@AllArgsConstructor
public class CommunitySubjectImageCreateDTO {

    @NotNull
    @ApiParam(value = "프로그램 id", required = true)
    private Long programId;
    @NotBlank
    @ApiParam(value = "토론 제목", required = true)
    private String subjectName;
    @NotBlank
    @ApiParam(value = "토론 내용", required = true)
    private String content;
    @ApiParam(value = "이미지", required = true)
    private MultipartFile image;

}
