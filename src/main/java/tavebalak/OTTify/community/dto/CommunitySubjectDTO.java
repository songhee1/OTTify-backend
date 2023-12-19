package tavebalak.OTTify.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tavebalak.OTTify.program.entity.Program;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class CommunitySubjectDTO {
    private Long subjectId;
    private String subjectName;
    private String content;
    private Long programId;
    private String programTitle;
    private String posterPath;

    @Builder
    public CommunitySubjectDTO(Long subjectId, String subjectName, String content, Long programId, String programTitle, String posterPath) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.content = content;
        this.programId = programId;
        this.programTitle = programTitle;
        this.posterPath = posterPath;
    }
}
