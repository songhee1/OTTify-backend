package tavebalak.OTTify.community.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "CommunitySubjectsDTO(프로그램별 토론 주제 조회 정보)", description = "총 갯수, 토론 주제 내용을 가진 Domain Class")
public class CommunitySubjectsDTO {

    @ApiModelProperty(value = "총 토론주제 갯수")
    private int subjectAmount;
    @ApiModelProperty(value = "토론 주제 리스트")
    private List<CommunitySubjectsListDTO> list;

    @Builder
    public CommunitySubjectsDTO(int subjectAmount, List<CommunitySubjectsListDTO> list) {
        this.subjectAmount = subjectAmount;
        this.list = list;
    }
}
