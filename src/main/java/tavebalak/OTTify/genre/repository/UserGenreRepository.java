package tavebalak.OTTify.genre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tavebalak.OTTify.genre.entity.UserGenre;

public interface UserGenreRepository extends JpaRepository<UserGenre, Long> {

    @Query("select ug from UserGenre ug where ug.user.id =:userId and ug.isFirst = true")
    UserGenre find1stGenreByUserId(Long userId);
}
