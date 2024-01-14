package tavebalak.OTTify.community.dto.request;

import io.swagger.annotations.ApiParam;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


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

    @Builder
    public CommunitySubjectCreateDTO(Long programId, String subjectName, String content) {
        this.programId = programId;
        this.subjectName = subjectName;
        this.content = content;
    }
}
