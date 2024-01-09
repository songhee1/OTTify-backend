package tavebalak.OTTify.program.dto.programDetails.openApiRequest.personDetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
public class OAProgramCreditsDto {
    private Long id;
    private List<Cast> cast;
    private List<Cast> crew;

    public void changeCast(List<Cast> cast){
        this.cast=cast;
    }
}
