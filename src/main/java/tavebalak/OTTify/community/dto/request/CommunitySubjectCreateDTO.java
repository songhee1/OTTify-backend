package tavebalak.OTTify.community.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CommunitySubjectCreateDTO {
    private Long programId;
    private String subjectName;
    private String content;
    private String programTitle;
    private String posterPath;

    @Builder
    public CommunitySubjectCreateDTO(Long programId, String subjectName, String content, String programTitle, String posterPath) {
        this.programId = programId;
        this.subjectName = subjectName;
        this.content = content;
        this.programTitle = programTitle;
        this.posterPath = posterPath;
    }
}
