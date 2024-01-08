package tavebalak.OTTify.program.dto.searchTrending.openApiRequest;

import lombok.Getter;

@Getter
public abstract class CommonOpenApiProgramInfo {
    private boolean adult;
    private String backdrop_path;
    private Long id;
    private String original_language;
    private String overview;
    private String poster_path;
    private double popularity;
    private double vote_average;
    private int vote_count;
}
