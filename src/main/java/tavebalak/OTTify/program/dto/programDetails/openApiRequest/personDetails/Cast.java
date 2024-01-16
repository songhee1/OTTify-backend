package tavebalak.OTTify.program.dto.programDetails.openApiRequest.personDetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Cast {

    // private long id;
    @JsonProperty("known_for_department")
    private String knownForDepartment;


    @JsonProperty("name")
    private String name;

    @JsonProperty("profile_path")
    private String profilePath;

    //영화 시에는 character 로만 사용


    @JsonProperty("character")
    private String character;

    //TV 는 character 와 department 사용

    @JsonProperty("department")
    private String department;

    public void changeKnownForDepartMent(String knownForDepartment) {
        this.knownForDepartment = knownForDepartment;
    }

    public void changeDepartment(String department) {
        this.department = department;
    }

}

