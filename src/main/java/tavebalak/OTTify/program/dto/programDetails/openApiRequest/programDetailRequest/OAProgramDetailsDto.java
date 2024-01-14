package tavebalak.OTTify.program.dto.programDetails.openApiRequest.programDetailRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import tavebalak.OTTify.program.dto.searchTrending.openApiRequest.CommonOpenApiProgramInfo;

import java.util.List;

@Getter
public class OAProgramDetailsDto extends CommonOpenApiProgramInfo {
    @JsonProperty("genres")
    public List<TmDbGenreInfo> tmDbGenreInfos;

    @JsonProperty("production_companies")
    private List<ProductionCompany> productionCompanies;

    @JsonProperty("production_countries")
    private List<OAProductionCountry> productionCountries;

    @JsonProperty("spoken_languages")
    private List<SpokenLanguage> spokenLanguages;

    private String status;

    private String tagline;
}

@Getter
class ProductionCompany {
    private int id;
    @JsonProperty("logo_path")
    private String logoPath;
    private String name;
    @JsonProperty("origin_country")
    private String originCountry;

    // Getters and Setters
}

@Getter
class SpokenLanguage {
    @JsonProperty("english_name")
    private String englishName;
    @JsonProperty("iso_639_1")
    private String iso6391;
    private String name;

    // Getters and Setters
}
