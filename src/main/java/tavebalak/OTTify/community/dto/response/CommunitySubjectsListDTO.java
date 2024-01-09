package tavebalak.OTTify.community.dto.response;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class CommunitySubjectsListDTO {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String title;
    private String nickName;
    private Long programId;
    private Long subjectId;
    private int likeCount;

    @Builder
    public CommunitySubjectsListDTO(LocalDateTime createdAt,
                                    LocalDateTime updatedAt,
                                    String title,
                                    String nickName,
                                    Long programId,
                                    Long subjectId,
                                    int likeCount
    ) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.title = title;
        this.nickName = nickName;
        this.programId = programId;
        this.subjectId = subjectId;
        this.likeCount = likeCount;
    }
}
