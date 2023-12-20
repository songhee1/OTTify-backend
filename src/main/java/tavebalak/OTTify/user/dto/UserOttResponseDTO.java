package tavebalak.OTTify.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserOttResponseDTO {
    private Long id;
    private String logoPath;

    @Builder
    public UserOttResponseDTO(Long id, String logoPath) {
        this.id = id;
        this.logoPath = logoPath;
    }
}
