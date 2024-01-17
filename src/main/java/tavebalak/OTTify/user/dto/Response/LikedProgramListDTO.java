package tavebalak.OTTify.user.dto.Response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class LikedProgramListDTO {
    @ApiModelProperty(value = "보고싶은 프로그램 수")
    private int totalCnt;

    @ApiModelProperty(value = "보고싶은 프로그램 리스트")
    private List<LikedProgramDTO> likedProgramList;

    @Builder
    public LikedProgramListDTO(int totalCnt, List<LikedProgramDTO> likedProgramList) {
        this.totalCnt = totalCnt;
        this.likedProgramList = likedProgramList;
    }
}
