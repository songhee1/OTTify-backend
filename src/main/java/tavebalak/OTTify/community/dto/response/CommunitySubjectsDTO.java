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
@ApiModel(value = "CommunitySubjectsDTO(토론주제글 구성 정보)", description = "생성일, 수정일, 토론주제, 글쓴이, 프로그램 id, 토론주제 id, 공감 수, 이미지 url 내용을 가진 Domain Class")
public class CommunitySubjectsDTO {

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
    @ApiModelProperty(value = "이미지 url")
    private String imageUrl;
    @ApiModelProperty(value = "해당 영화 이름")
    private String programName;
    @ApiModelProperty(value = "토론 내용")
    private String content;
    @ApiModelProperty(value = "댓글 수")
    private int commentCount;

    @Builder
    public CommunitySubjectsDTO(LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String title,
        String nickName,
        Long programId,
        Long subjectId,
        int likeCount,
        String imageUrl,
        String programName,
        String content,
        int commentCount
    ) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.title = title;
        this.nickName = nickName;
        this.programId = programId;
        this.subjectId = subjectId;
        this.likeCount = likeCount;
        this.imageUrl = imageUrl;
        this.programName = programName;
        this.content = content;
        this.commentCount = commentCount;
    }
}
