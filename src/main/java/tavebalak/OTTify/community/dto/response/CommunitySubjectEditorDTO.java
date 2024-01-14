package tavebalak.OTTify.community.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommunitySubjectEditorDTO {

    private String title;
    private String content;

    public CommunitySubjectEditorDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public CommunitySubjectEditorDTO changeTitleContentProgram(String subjectName, String content) {
        this.title = subjectName;
        this.content = content;
        return this;
    }
}
