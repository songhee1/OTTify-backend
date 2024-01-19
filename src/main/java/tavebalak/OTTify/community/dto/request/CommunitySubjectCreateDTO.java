package tavebalak.OTTify.community.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CommunitySubjectCreateDTO {

    @NotNull
    private Long programId;
    @NotBlank
    private String subjectName;
    @NotBlank
    private String content;

    @Builder
    public CommunitySubjectCreateDTO(Long programId, String subjectName, String content
    ) {
        this.programId = programId;
        this.subjectName = subjectName;
        this.content = content;
    }
}
