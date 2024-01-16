package tavebalak.OTTify.program.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.program.entity.ProgramType;

public interface ProgramRepository extends JpaRepository<Program, Long> {

    List<Program> findTop10ByOrderByAverageRatingDesc();

    boolean existsByTmDbProgramId(Long tmDbId);

    Program findByTmDbProgramId(Long tmDbId);

    boolean existsByTmDbProgramIdAndAndType(Long tmDbId, ProgramType type);

    Optional<Program> findByTmDbProgramIdAndType(Long tmDbId, ProgramType type);

}
