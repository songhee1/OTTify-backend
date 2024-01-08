package tavebalak.OTTify.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tavebalak.OTTify.review.entity.Review;
import tavebalak.OTTify.user.entity.LikedReview;

import java.util.List;
import java.util.Optional;

public interface LikedReviewRepository extends JpaRepository<LikedReview, Long> {
    Optional<LikedReview> findByUserIdAndReviewId(Long userId, Long reviewId);
    List<LikedReview> findByReviewId(Long reviewId);

    @Query("select lr.review from LikedReview lr where lr.user.id =:userId order by lr.review.createdAt")
    List<Review> findLikedReviewByUserId(@Param("userId") Long userId);
}
