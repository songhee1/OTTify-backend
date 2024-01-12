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
@ApiModel(value = "CommentListsDTO(토론 댓글 정보)", description = "댓글 id, 댓글 내용, 글쓴이, 생성일, 글쓴이 id, 공감 수, 대댓글 리스트 내용을 가진 Domain Class")
public class CommentListsDTO {

    @ApiModelProperty(value = "댓글 id")
    private Long commentId;
    @ApiModelProperty(value = "댓글 내용")
    private String content;
    @ApiModelProperty(value = "글쓴이")
    private String nickName;
    @ApiModelProperty(value = "작성일")
    private LocalDateTime createdAt;
    @ApiModelProperty(value = "글쓴이 id")
    private Long userId;
    @ApiModelProperty(value = "공감 수")
    private int likeCount;
    @ApiModelProperty(value = "대댓글 리스트")
    private List<ReplyListsDTO> replyListsDTOList;

    @Builder
    public CommentListsDTO(String content,
        String nickName,
        LocalDateTime createdAt,
        Long userId,
        List<ReplyListsDTO> replyListsDTOList,
        int likeCount,
        Long commentId
    ) {
        this.content = content;
        this.nickName = nickName;
        this.createdAt = createdAt;
        this.userId = userId;
        this.replyListsDTOList = replyListsDTOList;
        this.likeCount = likeCount;
        this.commentId = commentId;
    }
}
