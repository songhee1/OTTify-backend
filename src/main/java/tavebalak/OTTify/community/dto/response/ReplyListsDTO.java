package tavebalak.OTTify.community.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "ReplyListsDTO(토론 대댓글 정보)", description = "대댓글 id, 대댓글 내용, 글쓴이, 생성일, 글쓴이 id, 공감 수 내용을 가진 Domain Class")
public class ReplyListsDTO {

    @ApiModelProperty(value = "대댓글 id")
    private Long recommentId;
    @ApiModelProperty(value = "대댓글 내용")
    private String content;
    @ApiModelProperty(value = "글쓴이")
    private String nickName;
    @ApiModelProperty(value = "글쓴이 id")
    private Long userId;
    @ApiModelProperty(value = "생성일")
    private LocalDateTime createdAt;
    @ApiModelProperty(value = "공감 수")
    private int likeCount;

    @Builder
    public ReplyListsDTO(Long recommentId,
        String content,
        String nickName,
        Long userId,
        LocalDateTime createdAt,
        int likeCount) {
        this.recommentId = recommentId;
        this.content = content;
        this.nickName = nickName;
        this.userId = userId;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
    }
}
