package tavebalak.OTTify.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tavebalak.OTTify.program.entity.Program;

@Getter
@NoArgsConstructor
public class CommunitySubjectEditorDTO {
    private String title;
    private String content;
    private Program program;

    @Builder
    public CommunitySubjectEditorDTO(String title, String content, Program program) {
        this.title = title;
        this.content = content;
        this.program = program;
    }
}
