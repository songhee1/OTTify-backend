package tavebalak.OTTify.program.dto.programDetails.openApiRequest.personDetails;

import java.util.List;
import lombok.Getter;

@Getter
public class OAProgramCreditsDto {

    //private Long id;
    private List<Cast> cast;
    private List<Cast> crew;

    public void changeCast(List<Cast> cast) {
        this.cast = cast;
    }

    public void changeCrew(List<Cast> crew) {
        this.crew = crew;
    }
}
