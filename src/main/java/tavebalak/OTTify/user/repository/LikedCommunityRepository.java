package tavebalak.OTTify.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tavebalak.OTTify.user.entity.LikedCommunity;

public interface LikedCommunityRepository extends JpaRepository<LikedCommunity, Long> {
    Long countByCommunityId(Long communityId);
}
