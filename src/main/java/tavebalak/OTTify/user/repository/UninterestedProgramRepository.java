package tavebalak.OTTify.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import tavebalak.OTTify.user.entity.UninterestedProgram;

import java.util.List;

public interface UninterestedProgramRepository extends JpaRepository<UninterestedProgram, Long> {
    List<UninterestedProgram> findByUserId(@Param("userId") Long userId);
}
