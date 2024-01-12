package tavebalak.OTTify.program.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "RecommendProgramsDTO(추천 프로그램 정보)", description = "총 갯수, 프로그램 리스트 내용을 가진 Domain Class")
public class RecommendProgramsDTO {

    @ApiModelProperty(value = "총 갯수")
    private int recommentAmount;
    @ApiModelProperty(value = "프로그램 리스트")
    private List<ServiceListsDTO> serviceListsDTOList;

    @Builder
    public RecommendProgramsDTO(int recommentAmount, List<ServiceListsDTO> serviceListsDTOList) {
        this.recommentAmount = recommentAmount;
        this.serviceListsDTOList = serviceListsDTOList;
    }
}
