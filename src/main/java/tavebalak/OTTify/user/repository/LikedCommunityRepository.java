package tavebalak.OTTify.user.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tavebalak.OTTify.user.entity.LikedCommunity;

public interface LikedCommunityRepository extends JpaRepository<LikedCommunity, Long> {

    Optional<LikedCommunity> findByCommunityIdAndUserId(Long communityId, Long userId);

    List<LikedCommunity> findByCommunityId(Long communityId);

    int countByCommunityId(Long communityId);
}
