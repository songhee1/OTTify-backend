package tavebalak.OTTify.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tavebalak.OTTify.user.dto.LikedProgramResponseDTO;
import tavebalak.OTTify.user.entity.LikedProgram;

import java.util.List;

public interface LikedProgramRepository extends JpaRepository<LikedProgram, Long> {
    public List<LikedProgram> findByUserId(Long userId);

    @Query("select new tavebalak.OTTify.user.dto.LikedProgramResponseDTO(lp.program.id, lp.program.posterPath) from LikedProgram lp where lp.user.id =:userId")
    List<LikedProgramResponseDTO> findLikedProgram(@Param("userId") Long userId);
}
