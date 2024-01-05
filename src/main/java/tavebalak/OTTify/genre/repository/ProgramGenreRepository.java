package tavebalak.OTTify.genre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tavebalak.OTTify.genre.entity.ProgramGenre;

import java.util.List;

public interface ProgramGenreRepository extends JpaRepository<ProgramGenre, Long> {
    List<ProgramGenre> findByGenreId(Long genreId);
}
