package tavebalak.OTTify.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tavebalak.OTTify.community.entity.Reply;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    public List<Reply> findByCommunityIdAndParentId(Long communityId, Long parentId);
    public boolean existsByIdAndParentId(Long id, Long parentId);
}
