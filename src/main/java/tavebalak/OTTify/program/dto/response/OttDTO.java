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

    public OttDTO(Ott ott) {
        this.id = ott.getId();
        this.name = ott.getName();
    }
}
