package tavebalak.OTTify.user.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.user.entity.UninterestedProgram;
import tavebalak.OTTify.user.entity.User;

public interface UninterestedProgramRepository extends JpaRepository<UninterestedProgram, Long> {

    List<UninterestedProgram> findByUserId(@Param("userId") Long userId);

    @Query("select up from UninterestedProgram up join fetch up.program where up.user.id =:userId")
    List<UninterestedProgram> findByUserIdFetchJoin(@Param("userId") Long userId);


    Optional<UninterestedProgram> findByProgramAndUser(Program program, User user);

    boolean existsByProgramAndUser(Program program, User user);
}
