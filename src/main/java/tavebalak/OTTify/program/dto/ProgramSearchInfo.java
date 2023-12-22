package tavebalak.OTTify.program.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class ProgramSearchInfo {
    private String title;
    private String posterPath;
    private List<String> genreName=new ArrayList<>();

    public ProgramSearchInfo(String title, String posterPath, List<String> genreName){
        this.title=title;
        this.posterPath=posterPath;
        this.genreName=genreName;
    }

}
