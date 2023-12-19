package tavebalak.OTTify.genre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tavebalak.OTTify.genre.entity.UserGenre;

import java.util.List;

public interface UserGenreRepository extends JpaRepository<UserGenre, Long> {

    @Query("select ug.genre.name from UserGenre ug where ug.isFirst = true and ug.user.id =:userId")
    String findFirstGenre(@Param("userId") Long userId);

    @Query("select ug.genre.name from UserGenre ug where ug.isFirst = false and ug.user.id =:userId")
    List<String> findSecondGenre(@Param("userId") Long userId);

}
