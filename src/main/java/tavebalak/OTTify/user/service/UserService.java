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
    UserProfileDTO getUserProfile(Long userId);
    UserOttListDTO getUserOTT(Long userId);
    void update1stGenre(Long userId, GenreUpdateDTO updateRequestDTO);
    void update2ndGenre(Long userId, GenreUpdateDTO updateRequestDTO);
    Long updateUserProfile(Long userId, UserProfileUpdateDTO updateRequestDTO);
    Long updateUserOTT(Long userId, UserOttUpdateDTO updateRequestDTO);
    List<MyReviewDto> getMyReview(Long userId, Pageable pageable);
    List<MyReviewDto> getLikedReview(Long userId, Pageable pageable);
    List<MyDiscussionDto> getHostedDiscussion(Long userId, Pageable pageable);
    List<MyDiscussionDto> getParticipatedDiscussion(Long userId, Pageable pageable);
}
