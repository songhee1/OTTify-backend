package tavebalak.OTTify.program.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchResponseDtoV1 {
    private int movieCount;
    private List<ProgramTrendingWeekInfo> movieSearchInfos=new ArrayList<>();
    private int tvCount;
    private List<ProgramTrendingWeekInfo> tvSearchInfos = new ArrayList<>();
}
