package tavebalak.OTTify.program.dto.programDetails.openApiRequest.programDetailRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
@Getter
public class OATvDetailsDto extends OAProgramDetailsDto {

    @JsonProperty("created_by")
    private List<Creator> createdBy;
    @JsonProperty("episode_run_time")
    private List<Integer> episodeRunTime;
    @JsonProperty("first_air_date")
    private String firstAirDate;

    private String homepage;


    @JsonProperty("in_production")
    private boolean inProduction;
    private List<String> languages;
    @JsonProperty("last_air_date")
    private String lastAirDate;
    @JsonProperty("last_episode_to_air")
    private Episode lastEpisodeToAir;
    private String name;
    @JsonProperty("next_episode_to_air")
    private Object nextEpisodeToAir;
    private List<Network> networks;
    @JsonProperty("number_of_episodes")
    private int numberOfEpisodes;
    @JsonProperty("number_of_seasons")
    private int numberOfSeasons;
    @JsonProperty("origin_country")
    private List<String> originCountry;

    @JsonProperty("original_name")
    private String originalName;


    private List<Season> seasons;

    private String type;


    // Getters and Setters
}

class Creator {
    private int id;
    @JsonProperty("credit_id")
    private String creditId;
    private String name;
    private int gender;
    @JsonProperty("profile_path")
    private String profilePath;

    // Getters and Setters
}


class Episode {
    private int id;
    private String name;
    private String overview;
    @JsonProperty("vote_average")
    private double voteAverage;
    @JsonProperty("vote_count")
    private int voteCount;
    @JsonProperty("air_date")
    private String airDate;
    @JsonProperty("episode_number")
    private int episodeNumber;
    @JsonProperty("episode_type")
    private String episodeType;
    @JsonProperty("production_code")
    private String productionCode;
    private int runtime;
    @JsonProperty("season_number")
    private int seasonNumber;
    @JsonProperty("show_id")
    private int showId;
    @JsonProperty("still_path")
    private String stillPath;

    // Getters and Setters
}

class Network {
    private int id;
    @JsonProperty("logo_path")
    private String logoPath;
    private String name;
    @JsonProperty("origin_country")
    private String originCountry;

    // Getters and Setters
}

class Season {
    @JsonProperty("air_date")
    private String airDate;
    @JsonProperty("episode_count")
    private int episodeCount;
    private int id;
    private String name;
    private String overview;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("season_number")
    private int seasonNumber;
    @JsonProperty("vote_average")
    private double voteAverage;

    // Getters and Setters
}
