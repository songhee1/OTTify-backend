package tavebalak.OTTify.program.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tavebalak.OTTify.program.entity.Ott;

@Repository
public interface OttRepository extends JpaRepository<Ott, Long> {
}
