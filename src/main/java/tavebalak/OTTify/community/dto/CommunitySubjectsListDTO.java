package tavebalak.OTTify.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommunitySubjectsListDTO {
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String title;
    private String content;
    private String nickName;
    private Long programId;

    @Builder
    public CommunitySubjectsListDTO(LocalDateTime createdDate, LocalDateTime modifiedDate, String title, String content, String nickName, Long programId) {
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.title = title;
        this.content = content;
        this.nickName = nickName;
        this.programId = programId;
    }
}
