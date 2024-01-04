package tavebalak.OTTify.program.service;

import tavebalak.OTTify.program.dto.searchTrending.Response.SearchMovieResponseDto;
import tavebalak.OTTify.program.dto.searchTrending.Response.SearchResponseDto;
import tavebalak.OTTify.program.dto.searchTrending.Response.SearchTvResponseDto;
import tavebalak.OTTify.program.dto.searchTrending.Response.TrendingResponseDto;

public interface ProgramShowAndSaveService {
    public TrendingResponseDto showTrending();
    public SearchResponseDto searchByName(String name);

    public SearchMovieResponseDto searchByMovieName(String name, int page);

    public SearchTvResponseDto searchByTvName(String name, int page);
}
