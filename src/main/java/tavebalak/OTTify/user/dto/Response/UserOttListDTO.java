package tavebalak.OTTify.user.dto.Response;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserOttListDTO {

    @ApiModelProperty(value = "구독 중인 OTT 수")
    private int totalCnt;

    @ApiModelProperty(value = "구독 중인 OTT 리스트")
    private List<UserOttDTO> ottList;

    @Builder
    public UserOttListDTO(int totalCnt, List<UserOttDTO> ottList) {
        this.totalCnt = totalCnt;
        this.ottList = ottList;
    }
}
