package tavebalak.OTTify.program.dto.searchTrending.Response;

import lombok.Data;
import lombok.Getter;
import tavebalak.OTTify.program.dto.searchTrending.Response.ProgramTrendingDayInfo;
import tavebalak.OTTify.program.dto.searchTrending.Response.ProgramTrendingWeekInfo;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TrendingResponseDto {
    private List<ProgramTrendingDayInfo> programTrendingDayInfos = new ArrayList<>();
    private List<ProgramTrendingWeekInfo> programTrendingWeekInfos = new ArrayList<>();

    public TrendingResponseDto(List<ProgramTrendingDayInfo> programTrendingDayInfos, List<ProgramTrendingWeekInfo> programTrendingWeekInfos){
        this.programTrendingDayInfos = programTrendingDayInfos;
        this.programTrendingWeekInfos = programTrendingWeekInfos;
    }
}
