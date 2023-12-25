package tavebalak.OTTify.program.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OpenApiSearchTrendingDto {
    private int page;
    private List<SearchTrendingOpenApiProgramInfo> results=new ArrayList<>();
    private int total_pages;
    private int total_results;
}
