package tavebalak.OTTify.community.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class CommunitySubjectEditDTO {
    @NotNull
    private Long subjectId;
    @NotBlank(message = "토론 주제 제목이 비워져 있어서는 안됩니다.")
    private String subjectName;
    @NotBlank(message = "토론 주제 내용이 비워져 있어서는 안됩니다.")
    private String content;
    @NotNull
    private Long programId;
    @NotBlank(message = "프로그램 제목이 비워져 있어서는 안됩니다.")
    private String programTitle;
    @NotBlank(message = "프로그램 사진URL이 비워져 있어서는 안됩니다.")
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
