package tavebalak.OTTify.genre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tavebalak.OTTify.genre.entity.UserGenre;

import java.util.List;
import java.util.Optional;

public interface UserGenreRepository extends JpaRepository<UserGenre, Long> {
    public Optional<UserGenre> findByUserIdAndIsFirst(Long userId, boolean isFirst);
    public List<UserGenre> findByGenreId(Long genreId);
}
