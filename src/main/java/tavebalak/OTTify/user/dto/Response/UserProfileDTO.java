package tavebalak.OTTify.user.dto.Response;

import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tavebalak.OTTify.common.constant.GradeType;
import tavebalak.OTTify.genre.dto.GenreDTO;
import tavebalak.OTTify.review.dto.UserReviewRatingListDTO;

@Getter
@NoArgsConstructor
public class UserProfileDTO {

    @ApiModelProperty(value = "프로필 사진")
    private String profilePhoto;

    @ApiModelProperty(value = "닉네임")
    private String nickName;

    @ApiModelProperty(value = "등급")
    private GradeType grade;

    @ApiModelProperty(value = "이메일")
    private String email;

    @ApiModelProperty(value = "내 리뷰 평균 평점")
    private double averageRating;

    @ApiModelProperty(value = "작성한 리뷰 리스트")
    private UserReviewRatingListDTO ratingList;

    @ApiModelProperty(value = "1순위 취향 장르")
    private GenreDTO firstGenre;

    @ApiModelProperty(value = "2순위 취향 장르")
    private List<GenreDTO> secondGenre = new ArrayList<GenreDTO>();

    @ApiModelProperty(value = "구독 중인 OTT")
    private UserOttListDTO ott;

    @ApiModelProperty(value = "보고싶은 프로그램")
    private LikedProgramListDTO likedProgram;

    @ApiModelProperty(value = "관심없는 프로그램")
    private UninterestedProgramListDTO uninterestedProgram;

    @Builder
    public UserProfileDTO(String profilePhoto, String nickName, GradeType grade, String email,
        double averageRating, UserReviewRatingListDTO ratingList, GenreDTO firstGenre,
        List<GenreDTO> secondGenre, UserOttListDTO ott, LikedProgramListDTO likedProgram,
        UninterestedProgramListDTO uninterestedProgram) {
        this.profilePhoto = profilePhoto;
        this.nickName = nickName;
        this.grade = grade;
        this.email = email;
        this.averageRating = averageRating;
        this.ratingList = ratingList;
        this.firstGenre = firstGenre;
        this.secondGenre = secondGenre;
        this.ott = ott;
        this.likedProgram = likedProgram;
        this.uninterestedProgram = uninterestedProgram;
    }
}
