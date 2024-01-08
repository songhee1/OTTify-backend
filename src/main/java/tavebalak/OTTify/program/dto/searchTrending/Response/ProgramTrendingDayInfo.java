package tavebalak.OTTify.program.dto.searchTrending.Response;

import lombok.Data;
import lombok.Getter;

@Getter
public class ProgramTrendingDayInfo extends ProgramInfo {
    private String genreName;
    private String backDropPath;
    private String createdYear;
    private String rating;

    public ProgramTrendingDayInfo(Long programId, String title, String createdYear, String genreName, String backDropPath,double rating){
        super(programId,title);
        this.genreName=genreName;
        this.backDropPath=backDropPath;
        this.createdYear=createdYear;
        this.rating = String.format("%.1f", rating);
    }

}
