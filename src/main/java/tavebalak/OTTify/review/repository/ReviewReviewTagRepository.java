package tavebalak.OTTify.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tavebalak.OTTify.review.entity.ReviewReviewTag;
import tavebalak.OTTify.review.entity.ReviewTag;

import java.util.List;

public interface ReviewReviewTagRepository extends JpaRepository<ReviewReviewTag, Long> {

    @Query("select rrt.reviewTag from ReviewReviewTag rrt where rrt.review.id =:reviewId")
    List<ReviewTag> findReviewTagNameByReviewId(Long reviewId);
}
