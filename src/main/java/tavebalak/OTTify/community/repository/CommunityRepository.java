package tavebalak.OTTify.community.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tavebalak.OTTify.community.entity.Community;

public interface CommunityRepository extends JpaRepository<Community, Long> {

    Slice<Community> findCommunitiesBy(Pageable pageable);

    Slice<Community> findCommunitiesByProgramId(Pageable pageable, Long programId);

    @Query("select c from Community c join fetch c.program where c.user.id =:userId")
    Slice<Community> findByUserId(Long userId, Pageable pageable);
}
