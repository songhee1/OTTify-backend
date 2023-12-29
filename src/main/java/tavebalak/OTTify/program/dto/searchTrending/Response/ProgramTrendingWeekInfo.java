package tavebalak.OTTify.program.dto.searchTrending.Response;

import lombok.Data;

@Data
public class ProgramTrendingWeekInfo extends ProgramInfo {
    private String genreName;
    private String posterPath;
    private String createdYear;

    public ProgramTrendingWeekInfo(Long programId, String title, String createdYear, String genreName, String posterPath){
        super(programId,title);
        this.genreName=genreName;
        this.posterPath=posterPath;
        this.createdYear = createdYear;
    }
}
