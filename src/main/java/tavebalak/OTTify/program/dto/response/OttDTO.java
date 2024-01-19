package tavebalak.OTTify.program.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tavebalak.OTTify.program.entity.Ott;

@Getter
@NoArgsConstructor
public class OttDTO {

    @ApiModelProperty(value = "OTT id")
    private Long id;

    @ApiModelProperty(value = "OTT 이름")
    private String name;

    @ApiModelProperty(value = "구독 중인 OTT 로고 url")
    private String subscribeLogoPath;

    public OttDTO(Ott ott) {
        this.id = ott.getId();
        this.name = ott.getName();
        this.subscribeLogoPath = ott.getSubscribeLogoPath();
    }
}
