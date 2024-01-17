package tavebalak.OTTify.user.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tavebalak.OTTify.review.entity.Review;
import tavebalak.OTTify.user.entity.LikedReview;

public interface LikedReviewRepository extends JpaRepository<LikedReview, Long> {

    Optional<LikedReview> findByUserIdAndReviewId(Long userId, Long reviewId);

    Optional<List<LikedReview>> findByReviewId(Long reviewId);

    boolean existsByUserIdAndReviewId(Long userId, Long reviewId);
//    List<LikedReview> findByReviewId(Long reviewId);

    @Query("select lr.review from LikedReview lr where lr.user.id =:userId order by lr.review.createdAt")
    Slice<Review> findReviewByUserId(@Param("userId") Long userId, Pageable pageable);
}
