package tavebalak.OTTify.genre.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tavebalak.OTTify.genre.entity.UserGenre;

import java.util.List;

public interface UserGenreRepository extends JpaRepository<UserGenre, Long> {
    public List<UserGenre> findByGenreIdAndIsFirst(Long genreId, boolean isFirst);
}
