package tavebalak.OTTify.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tavebalak.OTTify.user.entity.LikedCommunity;

import java.util.Optional;

public interface LikedCommunityRepository extends JpaRepository<LikedCommunity, Long> {
    Optional<LikedCommunity> findByCommunityIdAndUserId(Long communityId, Long userId);
}
