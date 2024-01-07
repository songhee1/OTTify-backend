package tavebalak.OTTify.program.dto.searchTrending.Response;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SearchTvResponseDto {
    private List<ProgramSearchInfo> tvSearchInfos = new ArrayList<>();
    private int page;
    private int totalPages;
    private int totalResults;

    public SearchTvResponseDto(List<ProgramSearchInfo> tvSearchInfos, int page,int totalPages,int totalResults){
        this.tvSearchInfos = tvSearchInfos;
        this.page = page;
        this.totalPages = totalPages;
        this.totalResults = totalResults;

    }
}
