package tavebalak.OTTify.program.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchResponseDto {
    private List<ProgramSearchInfo> movieSearchInfos =new ArrayList<>();
    private List<ProgramSearchInfo> tvSearchInfos=new ArrayList<>();
}
