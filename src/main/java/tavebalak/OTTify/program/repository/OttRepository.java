package tavebalak.OTTify.program.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tavebalak.OTTify.program.entity.Ott;

public interface OttRepository extends JpaRepository<Ott, Long> {

    Optional<Ott> findByTmDbProviderId(Long tmDbProviderId);

    boolean existsByTmDbProviderId(Long tmDbProviderId);
}
