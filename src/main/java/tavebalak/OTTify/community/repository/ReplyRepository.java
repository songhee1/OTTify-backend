package tavebalak.OTTify.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tavebalak.OTTify.community.entity.Reply;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    public List<Reply> findByCommunityIdAndParentId(Long communityId, Long parentId);
    public List<Reply> findByIdAndParentId(Long id, Long parentId);
    public boolean existsByIdAndParentId(Long id, Long parentId);
    public Optional<Reply> findByIdAndParentIdIsNotNull(Long id);
    public Optional<Reply> findByIdAndParentIdIsNull(Long id);
}
