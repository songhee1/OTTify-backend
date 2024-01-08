package tavebalak.OTTify.user.Service;

import org.springframework.data.domain.Pageable;
import tavebalak.OTTify.community.dto.response.MyDiscussionDto;
import tavebalak.OTTify.review.dto.response.MyReviewDto;

import java.util.List;

public interface UserService {
    List<MyReviewDto> getMyReview(Long userId, Pageable pageable);
    List<MyReviewDto> getLikedReview(Long userId, Pageable pageable);
    List<MyDiscussionDto> getHostedDiscussion(Long userId, Pageable pageable);
    List<MyDiscussionDto> getParticipatedDiscussion(Long userId, Pageable pageable);
}
