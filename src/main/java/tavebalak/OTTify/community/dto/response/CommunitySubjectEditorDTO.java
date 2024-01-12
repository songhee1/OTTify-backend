package tavebalak.OTTify.community.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import tavebalak.OTTify.program.entity.Program;

@Getter
@NoArgsConstructor
public class CommunitySubjectEditorDTO {
    private String title;
    private String content;
    private Program program;

    public CommunitySubjectEditorDTO(String title, String content, Program program) {
        this.title = title;
        this.content = content;
        this.program = program;
    }

    public CommunitySubjectEditorDTO changeTitleContentProgram(String subjectName, String content, Program program) {
        this.title = subjectName;
        this.content = content;
        this.program = program;
        return this;
    }
}
