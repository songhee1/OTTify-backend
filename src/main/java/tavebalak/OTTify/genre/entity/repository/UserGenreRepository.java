package tavebalak.OTTify.genre.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tavebalak.OTTify.genre.entity.UserGenre;

import java.util.List;

public interface UserGenreRepository extends JpaRepository<UserGenre, Long> {
    public UserGenre findByUserIdAndIsFirst(Long userId, boolean isFirst);
    public List<UserGenre> findByGenreId(Long genreId);
}
