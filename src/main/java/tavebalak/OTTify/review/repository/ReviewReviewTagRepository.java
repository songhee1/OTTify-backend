package tavebalak.OTTify.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tavebalak.OTTify.review.entity.ReviewReviewTag;
import tavebalak.OTTify.review.entity.ReviewTag;

import javax.transaction.Transactional;
import java.util.List;

public interface ReviewReviewTagRepository extends JpaRepository<ReviewReviewTag, Long> {

    @Query("select rrt.reviewTag from ReviewReviewTag rrt where rrt.review.id =:reviewId")
    List<ReviewTag> findReviewTagNameByReviewId(@Param("reviewId") Long reviewId);

    @Query("select rrt from ReviewReviewTag rrt join fetch rrt.reviewTag where rrt.review.id=:reviewId")
    List<ReviewReviewTag> findByReviewWithReviewTagFetch(@Param("reviewId")Long reviewId);

    @Transactional
    @Modifying
    @Query("delete from ReviewReviewTag rrt where rrt.reviewTag.id in :reviewTagIds and rrt.review.id =:reviewId")
    void deleteAllByIdInQuery(@Param("reviewTagIds") List<Long> reviewTagIds, @Param("reviewId") Long reviewId);
}
