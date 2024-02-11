package tavebalak.OTTify.user.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import tavebalak.OTTify.community.dto.response.MyDiscussionDto;
import tavebalak.OTTify.genre.dto.request.GenreUpdateDTO;
import tavebalak.OTTify.review.dto.response.MyReviewDto;
import tavebalak.OTTify.user.dto.Request.UserOttUpdateDTO;
import tavebalak.OTTify.user.dto.Response.UserOttListDTO;
import tavebalak.OTTify.user.dto.Response.UserProfileDTO;

public interface UserService {
    UserProfileDTO getUserProfile();
    UserOttListDTO getUserOTT();
    void update1stGenre(GenreUpdateDTO updateRequestDTO);
    void update2ndGenre(GenreUpdateDTO updateRequestDTO);
    void updateUserProfile(String nickName, MultipartFile profilePhoto);
    void updateUserOTT(UserOttUpdateDTO updateRequestDTO);
    List<MyReviewDto> getMyReview(Pageable pageable);
    List<MyReviewDto> getLikedReview(Pageable pageable);
    List<MyDiscussionDto> getHostedDiscussion(Pageable pageable);
    List<MyDiscussionDto> getParticipatedDiscussion(Pageable pageable);
}
