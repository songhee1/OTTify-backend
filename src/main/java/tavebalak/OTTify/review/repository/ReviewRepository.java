package tavebalak.OTTify.review.repository;


import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.review.entity.Review;
import tavebalak.OTTify.user.entity.User;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Slice<Review> findByUserIdOrderByCreatedAt(Long userId, Pageable pageable);

    boolean existsByProgramAndUser(Program program, User user);


    Optional<Review> findByProgramIdAndUserId(Long programId, Long userId);


    @Query("select r from Review r join fetch r.user where r.program.id=:programId order by r.likeCounts desc")
    List<Review> findTop4ByProgramOrderByLikeCountsDescWithFetchUser(
        @Param("programId") Long programId, Pageable pageable);


    //Slice 로 전체 리뷰 가져오기
    @Query("select r from Review r join fetch r.user where r.program.id=:programId")
    Slice<Review> findByProgramWithFetchUser(@Param("programId") Long programId, Pageable pageable);


    //좋아요 많이 받은 순으로 user first genre 3개, 나랑 취향이 비슷한 user의 장르
    @Query("select r from Review r join fetch r.user where r.program.id=:programId and r.genre=:genre order by r.likeCounts desc")
    List<Review> findUserSpecific4ByProgramAndGenreOrderByLikeNumberDescWithFetchUser(
        @Param("programId") Long programId, @Param("genre") String genre, Pageable pageable);


    //좋아요 많이 받은 순으로 유저 first genre 같은 애들, 범위 전체
    @Query("select r from Review r join fetch r.user where r.program.id=:programId and r.genre=:genre")
    Slice<Review> findUserSpecificByProgramAndGenreWithFetchUser(@Param("programId") Long programId,
        @Param("genre") String genre, Pageable pageable);

    @Query("select count(r) from Review r where r.genre=:genre and r.program.id=:programId")
    int countByGenreName(@Param("genre") String genre, @Param("programId") Long programId);

    @Query("select sum(r.rating) from Review r where r.genre=:genre and r.program.id=:programId")
    Double sumReviewRatingByGenreName(@Param("genre") String genreName,
        @Param("programId") Long programId);

    List<Review> findByUserId(Long userId);

}
