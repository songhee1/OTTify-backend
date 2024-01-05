package tavebalak.OTTify.community.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tavebalak.OTTify.community.entity.Community;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    Page<Community> findCommunitiesBy(Pageable pageable);
    Page<Community> findCommunitiesByProgramId(Pageable pageable, Long programId);
}
