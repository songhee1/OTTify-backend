package tavebalak.OTTify.program.dto.searchTrending.Response;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Lob;

@Getter
public class ProgramSearchInfo extends ProgramInfo{
    private String genreName;
    private String posterPath;
    private String createdDate;

    @Lob
    private String overview;

    public ProgramSearchInfo(Long programId, String title, String createdDate, String genreName, String posterPath, String overview){
        super(programId,title);
        this.genreName=genreName;
        this.createdDate = createdDate;
        this.posterPath = posterPath;
        this.overview = overview;
    }
}
