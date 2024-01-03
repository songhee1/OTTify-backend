package tavebalak.OTTify.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tavebalak.OTTify.user.entity.LikedReply;

public interface LikedReplyRepository extends JpaRepository<LikedReply, Long> {
    Long countByCommunityId(Long communityId);
}