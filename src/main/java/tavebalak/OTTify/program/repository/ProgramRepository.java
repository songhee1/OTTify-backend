package tavebalak.OTTify.program.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tavebalak.OTTify.genre.entity.ProgramGenre;
import tavebalak.OTTify.genre.entity.UserGenre;
import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.user.entity.LikedProgram;
import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.program.entity.ProgramType;
import java.util.Optional;


import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {

    public List<Program> findTop10ByOrderByAverageRatingDesc();

    boolean existsByTmDbProgramId(Long tmDbId);
    Program findByTmDbProgramId(Long tmDbId);



    boolean existsByTmDbProgramIdAndAndType(Long tmDbId, ProgramType type);
    Optional<Program> findByTmDbProgramIdAndType(Long tmDbId, ProgramType type);

}
