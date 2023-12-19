package tavebalak.OTTify.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tavebalak.OTTify.review.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r.rating from Review r where r.user.id=:userId")
    List<Double> findReviewRatingByUserId(@Param("userId") Long userId);

}
