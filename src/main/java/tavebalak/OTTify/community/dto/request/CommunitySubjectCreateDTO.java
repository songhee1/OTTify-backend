package tavebalak.OTTify.community.dto.request;

import io.swagger.annotations.ApiParam;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Getter
@NoArgsConstructor
public class CommunitySubjectCreateDTO {

    @NotNull
    @ApiParam(value = "프로그램 id", required = true)
    private Long programId;
    @NotBlank
    @ApiParam(value = "토론 제목", required = true)
    private String subjectName;
    @NotBlank
    @ApiParam(value = "토론 내용", required = true)
    private String content;
    @ApiParam(value = "이미지", required = false)
    private MultipartFile image;

    @Builder
    public CommunitySubjectCreateDTO(Long programId, String subjectName, String content,
        MultipartFile image
    ) {
        this.programId = programId;
        this.subjectName = subjectName;
        this.content = content;
        this.image = image;
    }
}
