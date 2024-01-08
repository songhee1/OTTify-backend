package tavebalak.OTTify.program.dto.searchTrending.Response;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SearchMovieResponseDto {
    private List<ProgramSearchInfo> movieSearchInfos = new ArrayList<>();
    private int page;
    private int totalPages;
    private int totalResults;

    public SearchMovieResponseDto(List<ProgramSearchInfo> movieSearchInfos,int page,int totalPages, int totalResults){
        this.movieSearchInfos = movieSearchInfos;
        this.page = page;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }
}
