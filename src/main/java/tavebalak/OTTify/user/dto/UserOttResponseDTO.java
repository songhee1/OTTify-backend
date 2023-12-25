package tavebalak.OTTify.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tavebalak.OTTify.user.entity.UserSubscribingOTT;

@Getter
@NoArgsConstructor
public class UserOttResponseDTO {
    private Long id;
    private String name;
    private String logoPath;

    @Builder
    public UserOttResponseDTO(Long id, String name, String logoPath) {
        this.id = id;
        this.name = name;
        this.logoPath = logoPath;
    }

    public UserOttResponseDTO(UserSubscribingOTT uso) {
        this.id = uso.getOtt().getId();
        this.name = uso.getOtt().getName();
        this.logoPath = uso.getOtt().getLogoPath();
    }
}
