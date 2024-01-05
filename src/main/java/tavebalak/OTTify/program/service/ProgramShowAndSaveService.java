package tavebalak.OTTify.program.service;

import tavebalak.OTTify.program.dto.searchTrending.Response.SearchMovieResponseDto;
import tavebalak.OTTify.program.dto.searchTrending.Response.SearchResponseDto;
import tavebalak.OTTify.program.dto.searchTrending.Response.SearchTvResponseDto;
import tavebalak.OTTify.program.dto.searchTrending.Response.TrendingResponseDto;

public interface ProgramShowAndSaveService {
     TrendingResponseDto showTrending();
     SearchResponseDto searchByName(String name);

     SearchMovieResponseDto searchByMovieName(String name, int page);

     SearchTvResponseDto searchByTvName(String name, int page);
}
