package tavebalak.OTTify.review.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import tavebalak.OTTify.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Slice<Review> findByUserIdOrderByCreatedAt(@Param("userId") Long userId, Pageable pageable);
}
