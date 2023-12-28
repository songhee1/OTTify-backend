package tavebalak.OTTify.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommunitySubjectsListDTO {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String title;
    private String content;
    private String nickName;
    private Long programId;

    @Builder
    public CommunitySubjectsListDTO(LocalDateTime createdAt, LocalDateTime updatedAt, String title, String content, String nickName, Long programId) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.title = title;
        this.content = content;
        this.nickName = nickName;
        this.programId = programId;
    }
}
