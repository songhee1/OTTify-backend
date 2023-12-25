package tavebalak.OTTify.program.dto;

import lombok.Data;

@Data
public class ProgramTrendingDayInfo extends ProgramInfo {
    private String genreName;
    private String backDropPath;
    private String createdYear;

    public ProgramTrendingDayInfo(Long programId, String title, String createdYear, String genreName, String backDropPath){
        super(programId,title);
        this.genreName=genreName;
        this.backDropPath=backDropPath;
        this.createdYear=createdYear;
    }

}
