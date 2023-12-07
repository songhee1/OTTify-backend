package tavebalak.OTTify.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class CommunitySubjectsListDTO {
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String title;
    private String content;
    private String nickName;
    private Long programId;

    @Builder
    public CommunitySubjectsListDTO(Timestamp createdDate, Timestamp modifiedDate, String title, String content, String nickName, Long programId) {
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.title = title;
        this.content = content;
        this.nickName = nickName;
        this.programId = programId;
    }
}
