package tavebalak.OTTify.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tavebalak.OTTify.user.entity.LikedReply;

import java.util.List;
import java.util.Optional;

public interface LikedReplyRepository extends JpaRepository<LikedReply, Long> {
    Optional<LikedReply> findByUserIdAndReplyIdAndCommunityId(Long userId, Long replyId, Long communityId);
    List<LikedReply> findByCommunityIdAndReplyId(Long communityId, Long replyId);
    Long countByCommunityId(Long communityId);
}
