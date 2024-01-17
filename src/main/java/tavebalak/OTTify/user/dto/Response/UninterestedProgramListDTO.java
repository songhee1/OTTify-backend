package tavebalak.OTTify.user.dto.Response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class UninterestedProgramListDTO {
    @ApiModelProperty(value = "관심없는 프로그램 수")
    private int totalCnt;

    @ApiModelProperty(value = "관심없는 프로그램 리스트")
    private List<UninterestedProgramDTO> uninterestedProgramList;

    @Builder
    public UninterestedProgramListDTO(int totalCnt, List<UninterestedProgramDTO> uninterestedProgramList) {
        this.totalCnt = totalCnt;
        this.uninterestedProgramList = uninterestedProgramList;
    }
}
