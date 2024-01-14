package tavebalak.OTTify.community.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "CommunityAriclesDTO(토론 주제글 조회)", description = "토론 주제, 글쓴이, 글쓴이 id, 내용, 작성일, 수정일, 댓글 수, 공감 수, 토론 주제 id, 댓글 리스트 내용을 가진 Domain Class")
public class CommunityAriclesDTO {

    @ApiModelProperty(value = "토론 주제")
    private String title;
    @ApiModelProperty(value = "글쓴이")
    private String writer;
    @ApiModelProperty(value = "글쓴이 id")
    private Long userId;
    @ApiModelProperty(value = "토론 내용")
    private String content;
    @ApiModelProperty(value = "작성일")
    private LocalDateTime createdAt;
    @ApiModelProperty(value = "수정일")
    private LocalDateTime updatedAt;
    @ApiModelProperty(value = "댓글 수")
    private int commentAmount;
    @ApiModelProperty(value = "공감 수")
    private int likeCount;
    @ApiModelProperty(value = "토론 주제 id")
    private Long subjectId;
    @ApiModelProperty(value = "프로그램 제목")
    private String programTitle;
    @ApiModelProperty(value = "댓글 리스트")
    private List<CommentListsDTO> commentListsDTOList;

    @Builder
    public CommunityAriclesDTO(String title,
        String writer,
        Long userId,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        int commentAmount,
        List<CommentListsDTO> commentListsDTOList,
        int likeCount,
        Long subjectId,
        String programTitle
    ) {
        this.title = title;
        this.writer = writer;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.commentAmount = commentAmount;
        this.commentListsDTOList = commentListsDTOList;
        this.likeCount = likeCount;
        this.subjectId = subjectId;
        this.programTitle = programTitle;
    }
}
