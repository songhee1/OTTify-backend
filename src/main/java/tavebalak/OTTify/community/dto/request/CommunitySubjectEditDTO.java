package tavebalak.OTTify.community.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommunitySubjectEditDTO {

    @NotNull
    private Long subjectId;
    @NotBlank(message = "토론 주제 제목이 비워져 있어서는 안됩니다.")
    private String subjectName;
    @NotBlank(message = "토론 주제 내용이 비워져 있어서는 안됩니다.")
    private String content;
    private String imageUrl;

    @Builder
    public CommunitySubjectEditDTO(Long subjectId, String subjectName, String content,
        String imageUrl) {
        this.content = content;
        this.subjectName = subjectName;
        this.subjectId = subjectId;
        this.imageUrl = imageUrl;
    }

}
