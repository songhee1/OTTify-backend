package tavebalak.OTTify.user.service;

import org.springframework.data.domain.Pageable;
import tavebalak.OTTify.community.dto.response.MyDiscussionDto;
import tavebalak.OTTify.review.dto.response.MyReviewDto;
import tavebalak.OTTify.user.dto.Request.UserOttUpdateDTO;
import tavebalak.OTTify.user.dto.Request.UserProfileUpdateDTO;
import tavebalak.OTTify.user.dto.Response.UserOttListDTO;
import tavebalak.OTTify.user.dto.Response.UserProfileDTO;
import tavebalak.OTTify.genre.dto.request.GenreUpdateDTO;

import java.util.List;

public interface UserService {
    UserProfileDTO getUserProfile();
    UserOttListDTO getUserOTT();
    void update1stGenre(GenreUpdateDTO updateRequestDTO);
    void update2ndGenre(GenreUpdateDTO updateRequestDTO);
    Long updateUserProfile(UserProfileUpdateDTO updateRequestDTO);
    Long updateUserOTT(List<UserOttUpdateDTO> updateRequestDTO);
    List<MyReviewDto> getMyReview(Pageable pageable);
    List<MyReviewDto> getLikedReview(Pageable pageable);
    List<MyDiscussionDto> getHostedDiscussion(Pageable pageable);
    List<MyDiscussionDto> getParticipatedDiscussion(Pageable pageable);
}
