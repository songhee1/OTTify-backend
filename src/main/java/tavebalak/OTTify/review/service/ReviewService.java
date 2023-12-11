package tavebalak.OTTify.review.service;

import tavebalak.OTTify.review.dto.LatestReviewsDTO;

import java.util.List;

public interface ReviewService {
    public List<LatestReviewsDTO> getLatestReviews();
}
