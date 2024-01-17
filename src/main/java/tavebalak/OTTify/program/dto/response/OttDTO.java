package tavebalak.OTTify.program.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import tavebalak.OTTify.program.entity.Ott;

@Getter
@NoArgsConstructor
public class OttDTO {
    private Long id;
    private String name;

    public OttDTO(Ott ott) {
        this.id = ott.getId();
        this.name = ott.getName();
    }
}
