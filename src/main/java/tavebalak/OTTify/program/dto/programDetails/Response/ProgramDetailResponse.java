package tavebalak.OTTify.program.dto.programDetails.Response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
public class ProgramDetailResponse {

    private String title;
    private String originalTitle;

    private String createdDate;

    private List<String> genreName;

    private String country;

    private String posterPath;

    private String overview;

    private String tagline;

    private String backDropPath;

    @Builder
    public ProgramDetailResponse(String title,
                                 String originalTitle,
                                 String createdDate,
                                 List<String> genreName,
                                 String country,
                                 String posterPath,
                                 String overview,
                                 String tagline,
                                 String backDropPath){
        this.title=title;
        this.originalTitle=originalTitle;
        this.createdDate=createdDate;
        this.genreName=genreName;
        this.country=country;
        this.posterPath=posterPath;
        this.overview= overview;
        this.tagline=tagline;
        this.backDropPath=backDropPath;
    }
}