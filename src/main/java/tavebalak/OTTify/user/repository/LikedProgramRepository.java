package tavebalak.OTTify.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tavebalak.OTTify.user.dto.LikedProgramDTO;
import tavebalak.OTTify.user.entity.LikedProgram;

import java.util.List;

public interface LikedProgramRepository extends JpaRepository<LikedProgram, Long> {

    @Query("select new tavebalak.OTTify.user.dto.LikedProgramDTO(lp.program.id, lp.program.posterPath) from LikedProgram lp where lp.user.id =:userId")
    List<LikedProgramDTO> findLikedProgram(@Param("userId") Long userId);
}
