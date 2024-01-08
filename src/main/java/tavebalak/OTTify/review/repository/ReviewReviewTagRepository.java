package tavebalak.OTTify.review.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tavebalak.OTTify.review.entity.ReviewReviewTag;

import java.util.List;

public interface ReviewReviewTagRepository extends JpaRepository<ReviewReviewTag,Long> {
    @Query("select rrt from ReviewReviewTag rrt join fetch rrt.reviewTag where rrt.review.id=:reviewId")
    List<ReviewReviewTag> findByReviewWithReviewTagFetch(@Param("reviewId")Long reviewId);
}
