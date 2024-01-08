package tavebalak.OTTify.review.service;

import tavebalak.OTTify.review.dto.LatestReviewsDTO;
import tavebalak.OTTify.review.entity.Review;

import java.util.List;

public interface ReviewService {
    List<LatestReviewsDTO> getLatestReviews();
    void save(Review review);
    boolean likeReview(Long id);
}
