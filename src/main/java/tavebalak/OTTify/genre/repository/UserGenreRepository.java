package tavebalak.OTTify.genre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import tavebalak.OTTify.genre.entity.UserGenre;

import java.util.List;

public interface UserGenreRepository extends JpaRepository<UserGenre, Long> {

    @Query("select ug from UserGenre ug where ug.user.id =:userId and ug.isFirst = true")
    UserGenre find1stGenreByUserId(@Param("userId") Long userId);

    @Query("select ug from UserGenre ug where ug.user.id =:userId and ug.isFirst = false")
    List<UserGenre> find2ndGenreByUserId(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query("delete from UserGenre ug where ug.genre.id in :genreIds and ug.user.id =:userId")
    void deleteAllByIdInQuery(@Param("genreIds") List<Long> genreIds, Long userId);
}
