package tavebalak.OTTify.program.dto.response;

import lombok.Getter;

@Getter
public class UserSpecificRatingResponseDto {

    private String usersFirstGenreName;
    private String usersFirstGenreProgramRating;

    public UserSpecificRatingResponseDto(String usersFirstGenreName,
        double usersFirstGenreProgramRating) {
        this.usersFirstGenreName = usersFirstGenreName;
        this.usersFirstGenreProgramRating = String.format("%.1f", usersFirstGenreProgramRating);
    }

}
