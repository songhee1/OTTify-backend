package tavebalak.OTTify.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentDTO {
    private Long commentId;
    private Long subjectId;
    private String content;

    @Builder
    public CommentDTO(Long commentId, String content, Long subjectId){
        this.commentId = commentId;
        this.content = content;
        this.subjectId = subjectId;
    }
}
