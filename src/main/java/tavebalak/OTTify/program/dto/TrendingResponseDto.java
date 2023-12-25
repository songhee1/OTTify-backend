package tavebalak.OTTify.program.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TrendingResponseDto {
    private List<ProgramTrendingDayInfo> programTrendingDayInfos = new ArrayList<>();
    private List<ProgramTrendingWeekInfo> programTrendingWeekInfos = new ArrayList<>();
}
