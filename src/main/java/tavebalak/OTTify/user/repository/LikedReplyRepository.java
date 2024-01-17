package tavebalak.OTTify.user.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tavebalak.OTTify.user.entity.LikedReply;

public interface LikedReplyRepository extends JpaRepository<LikedReply, Long> {

    Optional<LikedReply> findByUserIdAndReplyIdAndCommunityId(Long userId, Long replyId, Long communityId);

    List<LikedReply> findByCommunityIdAndReplyId(Long communityId, Long replyId);

    int countByCommunityId(Long communityId);
}
