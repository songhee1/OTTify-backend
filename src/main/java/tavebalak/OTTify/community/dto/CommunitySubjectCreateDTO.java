package tavebalak.OTTify.community.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommunitySubjectCreateDTO {
    private Long programId;
    private String subjectName;
    private String content;
    private String programTitle;
    private String posterPath;
}
