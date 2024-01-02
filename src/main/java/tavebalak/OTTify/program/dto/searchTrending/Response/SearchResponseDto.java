package tavebalak.OTTify.program.dto.searchTrending.Response;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Getter
public class SearchResponseDto {
    private int movieCount;
    private int tvCount;
    private SearchTvResponseDto searchTvResponseDto;

    public SearchResponseDto(int movieCount,int tvCount,SearchTvResponseDto searchTvResponseDto){
        this.movieCount = movieCount;
        this.tvCount = tvCount;
        this.searchTvResponseDto = searchTvResponseDto;
    }
}
