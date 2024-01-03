package tavebalak.OTTify.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tavebalak.OTTify.review.entity.Review;
import tavebalak.OTTify.user.entity.LikedReview;

import java.util.List;

public interface LikedReviewRepository extends JpaRepository<LikedReview, Long> {
    @Query("select lr.review from LikedReview lr where lr.user.id =:userId")
    List<Review> findLikedReviewByUserId(Long userId);
}
