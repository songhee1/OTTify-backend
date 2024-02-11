package tavebalak.OTTify.program.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class OttListDTO {
    List<OttDTO> ottList = new ArrayList<>();

    public OttListDTO(List<OttDTO> ottList) {
        this.ottList = ottList;
    }
}
