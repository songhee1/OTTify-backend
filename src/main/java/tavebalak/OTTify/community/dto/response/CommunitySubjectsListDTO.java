package tavebalak.OTTify.community.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "CommunitySubjectsListDTO(프로그램별 토론 주제 조회 정보)", description = "총 갯수, 토론 주제 내용을 가진 Domain Class")
public class CommunitySubjectsListDTO {

    @ApiModelProperty(value = "총 토론주제 갯수")
    private int subjectAmount;
    @ApiModelProperty(value = "토론 주제 리스트")
    private List<CommunitySubjectsDTO> list;
    @ApiModelProperty(value = "다음 페이지 여부")
    private boolean hasNext;

    @Builder
    public CommunitySubjectsListDTO(int subjectAmount, List<CommunitySubjectsDTO> list,
        boolean hasNext) {
        this.subjectAmount = subjectAmount;
        this.list = list;
        this.hasNext = hasNext;
    }
}
