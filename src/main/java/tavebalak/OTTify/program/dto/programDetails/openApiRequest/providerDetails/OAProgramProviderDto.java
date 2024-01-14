package tavebalak.OTTify.program.dto.programDetails.openApiRequest.providerDetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAProgramProviderDto {
    @JsonProperty("id")
    private Long id;


    @JsonProperty("results")
    private Map<String, OACountryDetailsDto> results;
}
