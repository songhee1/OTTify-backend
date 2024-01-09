package tavebalak.OTTify.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tavebalak.OTTify.user.entity.LikedProgram;
import tavebalak.OTTify.user.entity.UninterestedProgram;
import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.user.entity.UninterestedProgram;
import tavebalak.OTTify.user.entity.User;

import java.util.Optional;
import java.util.List;

public interface UninterestedProgramRepository extends JpaRepository<UninterestedProgram, Long> {
    List<UninterestedProgram> findByUserId(@Param("userId") Long userId);

    @Query("select up from UninterestedProgram up join fetch up.program where up.user.id =:userId")
    List<UninterestedProgram> findByUserIdFetchJoin(@Param("userId") Long userId);


    Optional<UninterestedProgram> findByProgramAndUser(Program program, User user);

    boolean existsByProgramAndUser(Program program,User user);
}
