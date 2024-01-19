package tavebalak.OTTify.review.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import tavebalak.OTTify.review.dto.reviewresponse.FourReviewResponseWithCounts;
import tavebalak.OTTify.review.dto.reviewresponse.ReviewProgramResponseDto;
import tavebalak.OTTify.user.entity.User;

public interface ReviewShowProgramDetailService {

    ReviewProgramResponseDto showMyReview(User user, Long programId);

    FourReviewResponseWithCounts show4Review(Long programId);

    Slice<ReviewProgramResponseDto> showReviewList(Long programId, Pageable pageable);

    FourReviewResponseWithCounts show4UserSpecificReviewList(User user, Long programId);

    Slice<ReviewProgramResponseDto> showUserSpecificReviewList(User user, Long programId,
        Pageable pageable);


}
