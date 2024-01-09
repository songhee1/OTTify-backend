package tavebalak.OTTify.program.dto.programDetails.openApiRequest.programDetailRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class OAMovieDetailsDto extends OAProgramDetailsDto {

    @JsonProperty("belongs_to_collection")
    private Collection belongsToCollection;
    private int budget;

    private String homepage;

    @JsonProperty("imdb_id")
    private String imdbId;

    @JsonProperty("original_title")
    private String originalTitle;



    @JsonProperty("release_date")
    private String releaseDate;
    private long revenue;
    private int runtime;

    private String title;
    private boolean video;

}
class Collection {
    private int id;
    private String name;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("backdrop_path")
    private String backdropPath;

    // Getters and Setters
}
