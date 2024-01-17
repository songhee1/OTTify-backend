package tavebalak.OTTify.user.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.user.entity.LikedProgram;
import tavebalak.OTTify.user.entity.User;

public interface LikedProgramRepository extends JpaRepository<LikedProgram, Long> {

    List<LikedProgram> findByUserId(@Param("userId") Long userId);

    @Query("select lp from LikedProgram lp join fetch lp.program where lp.user.id =:userId")
    List<LikedProgram> findByUserIdFetchJoin(@Param("userId") Long userId);


    Optional<LikedProgram> findByProgramAndUser(Program program, User user);

    boolean existsByProgramAndUser(Program program, User user);
}
