package tavebalak.OTTify.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReplyCommentEditorDTO {
    private String comment;
    @Builder
    public ReplyCommentEditorDTO(String comment){
        this.comment = comment;
    }
}
