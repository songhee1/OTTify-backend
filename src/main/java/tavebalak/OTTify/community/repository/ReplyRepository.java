package tavebalak.OTTify.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tavebalak.OTTify.community.entity.Community;
import tavebalak.OTTify.community.entity.Reply;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    public List<Reply> findByCommunityIdAndParentId(Long communityId, Long parentId);
    public List<Reply> findByIdAndParentId(Long id, Long parentId);
    public boolean existsByIdAndParentId(Long id, Long parentId);
    public Optional<Reply> findByCommunityIdAndIdAndParentIdIsNotNull(Long communityId, Long id);
    public Optional<Reply> findByCommunityIdAndIdAndParentIdIsNull(Long communityId, Long id);

    @Query("select distinct r.community from Reply r where r.user.id =:userId order by r.community.createdAt")
    List<Community> findAllCommunityByUserId(@Param("userId") Long userId);
}
