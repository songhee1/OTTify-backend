package tavebalak.OTTify.genre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tavebalak.OTTify.genre.entity.Genre;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre,Long> {
    boolean existsByTmDbGenreId(Long tmDbGenreId);

    Optional<Genre> findByTmDbGenreId(Long tmDbGenreId);
}
