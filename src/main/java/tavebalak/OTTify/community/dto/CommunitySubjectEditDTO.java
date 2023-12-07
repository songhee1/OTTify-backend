package tavebalak.OTTify.community.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommunitySubjectEditDTO {
    private Long subjectId;
    private String subjectName;
    private String content;
    private Long programId;
    private String programTitle;
    private String posterPath;

    @Builder
    public CommunitySubjectEditDTO(Long subjectId, String subjectName, String content, Long programId, String programTitle, String posterPath){
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.content = content;
        this.programId = programId;
        this.programTitle = programTitle;
        this.posterPath = posterPath;
    }
}
