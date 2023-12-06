package tavebalak.OTTify.program.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tavebalak.OTTify.program.entity.Program;

public interface ProgramRepository extends JpaRepository<Program, Long> {
}
