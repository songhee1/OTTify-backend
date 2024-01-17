package tavebalak.OTTify.user.dto.Response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tavebalak.OTTify.user.entity.UserSubscribingOTT;

@Getter
@NoArgsConstructor
public class UserOttDTO {
    @ApiModelProperty(value = "OTT id")
    private Long id;

    @ApiModelProperty(value = "OTT 이름")
    private String name;

    @ApiModelProperty(value = "OTT 로고 url")
    private String logoPath;

    @Builder
    public UserOttDTO(Long id, String name, String logoPath) {
        this.id = id;
        this.name = name;
        this.logoPath = logoPath;
    }

    public UserOttDTO(UserSubscribingOTT uso) {
        this.id = uso.getOtt().getId();
        this.name = uso.getOtt().getName();
        this.logoPath = uso.getOtt().getLogoPath();
    }
}
