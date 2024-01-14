package tavebalak.OTTify.community.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import tavebalak.OTTify.community.entity.Community;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    Page<Community> findCommunitiesBy(Pageable pageable);
    Page<Community> findCommunitiesByProgramId(Pageable pageable, Long programId);
    Slice<Community> findByUserId(Long userId, Pageable pageable);
}
