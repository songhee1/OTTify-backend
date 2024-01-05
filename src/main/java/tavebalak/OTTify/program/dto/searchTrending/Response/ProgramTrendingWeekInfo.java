package tavebalak.OTTify.program.dto.searchTrending.Response;

import lombok.Data;
import lombok.Getter;

@Getter
public class ProgramTrendingWeekInfo extends ProgramInfo {
    private String genreName;
    private String posterPath;
    private String createdYear;
    private String rating;

    public ProgramTrendingWeekInfo(Long programId, String title, String createdYear, String genreName, String posterPath,double rating){
        super(programId,title);
        this.genreName=genreName;
        this.posterPath=posterPath;
        this.createdYear = createdYear;
        this.rating = String.format("%.1f", rating);
    }
}
