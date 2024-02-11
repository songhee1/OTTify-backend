package tavebalak.OTTify.community.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommunitySubjectImageEditorDTO {

    private String title;
    private String content;
    private String imageUrl;

    public CommunitySubjectImageEditorDTO(String title, String content, String imageUrl) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
    }


    public CommunitySubjectImageEditorDTO changeTitleContentImage(String subjectName,
        String content, String imageUrl) {
        this.title = subjectName;
        this.content = content;
        this.imageUrl = imageUrl;
        return this;
    }

}
