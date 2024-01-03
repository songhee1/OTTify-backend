package tavebalak.OTTify.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tavebalak.OTTify.review.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserId(Long userId);
}
