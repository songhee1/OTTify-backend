package tavebalak.OTTify.program.dto.searchTrending.Response;

import lombok.Data;
import tavebalak.OTTify.program.dto.searchTrending.Response.ProgramTrendingDayInfo;
import tavebalak.OTTify.program.dto.searchTrending.Response.ProgramTrendingWeekInfo;

import java.util.ArrayList;
import java.util.List;

@Data
public class TrendingResponseDto {
    private List<ProgramTrendingDayInfo> programTrendingDayInfos = new ArrayList<>();
    private List<ProgramTrendingWeekInfo> programTrendingWeekInfos = new ArrayList<>();
}
