package tavebalak.OTTify.community.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
@ApiModel(value = "CommunitySubjectsListDTO(토론주제글 구성 정보)", description = "생성일, 수정일, 토론주제, 글쓴이, 프로그램 id, 토론주제 id, 공감 수 내용을 가진 Domain Class")
public class CommunitySubjectsListDTO {

    @ApiModelProperty(value = "글 생성일")
    private LocalDateTime createdAt;
    @ApiModelProperty(value = "글 수정일")
    private LocalDateTime updatedAt;
    @ApiModelProperty(value = "토론주제 제목")
    private String title;
    @ApiModelProperty(value = "글쓴이")
    private String nickName;
    @ApiModelProperty(value = "프로그램 id")
    private Long programId;
    @ApiModelProperty(value = "토론주제 id")
    private Long subjectId;
    @ApiModelProperty(value = "공감 수")
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
