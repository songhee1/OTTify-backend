package tavebalak.OTTify.program.dto.programDetails.openApiRequest.programDetailRequest;

import lombok.Getter;

@Getter
public class TmDbGenreInfo {
    private Long id;
    private String name;

    public void changeName(String name){
        this.name= name;

    }
}
