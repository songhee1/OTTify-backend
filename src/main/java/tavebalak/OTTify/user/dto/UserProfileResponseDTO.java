package tavebalak.OTTify.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tavebalak.OTTify.review.dto.UserReviewRatingListDTO;
import tavebalak.OTTify.user.entity.UserType;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class UserProfileResponseDTO {
    private String profilePhoto;
    private String nickName;
    private UserType userType;
    private String email;

    private double averageRating;
    private UserReviewRatingListDTO ratingList;

    private String firstGenre;
    private List<String> secondGenre = new ArrayList<String>();

    private List<LikedProgramDTO> likedProgram = new ArrayList<>();
    private List<UninterestedProgramDTO> uninterestedProgram = new ArrayList<>();

    @Builder
    public UserProfileResponseDTO(String profilePhoto, String nickName, UserType userType, String email, double averageRating, UserReviewRatingListDTO ratingList, String firstGenre, List<String> secondGenre, List<LikedProgramDTO> likedProgram, List<UninterestedProgramDTO> uninterestedProgram) {
        this.profilePhoto = profilePhoto;
        this.nickName = nickName;
        this.userType = userType;
        this.email = email;
        this.averageRating = averageRating;
        this.ratingList = ratingList;
        this.firstGenre = firstGenre;
        this.secondGenre = secondGenre;
        this.likedProgram = likedProgram;
        this.uninterestedProgram = uninterestedProgram;
    }
}
