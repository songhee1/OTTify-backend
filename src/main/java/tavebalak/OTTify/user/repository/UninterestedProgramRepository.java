package tavebalak.OTTify.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tavebalak.OTTify.user.dto.UninterestedProgramResponseDTO;
import tavebalak.OTTify.user.entity.UninterestedProgram;

import java.util.List;

public interface UninterestedProgramRepository extends JpaRepository<UninterestedProgram, Long> {

    @Query("select new tavebalak.OTTify.user.dto.UninterestedProgramResponseDTO(up.program.id, up.program.posterPath) from UninterestedProgram up where up.user.id =:userId")
    List<UninterestedProgramResponseDTO> findUninterestedProgram(@Param("userId") Long userId);
}
