package tavebalak.OTTify.genre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tavebalak.OTTify.genre.entity.ProgramGenre;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tavebalak.OTTify.genre.entity.Genre;
import tavebalak.OTTify.genre.entity.ProgramGenre;
import tavebalak.OTTify.program.entity.Program;

import java.util.List;
import java.util.Optional;

public interface ProgramGenreRepository extends JpaRepository<ProgramGenre, Long> {
    List<ProgramGenre> findByGenreId(Long genreId);

    @Query("select pg from ProgramGenre pg join fetch pg.genre where pg.program.id=:programId")
    List<ProgramGenre> findByProgram(@Param("programId") Long programId);

    @Query("select pg from ProgramGenre pg join fetch pg.genre where pg.genre.tmDbGenreId=:genreId and pg.program.id=:programId")
    ProgramGenre findByGenreIdAndProgramIdWithFetch(@Param("genreId") Long genreId,@Param("programId") Long programId);


}
