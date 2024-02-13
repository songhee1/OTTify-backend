package tavebalak.OTTify.community.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tavebalak.OTTify.community.entity.Community;
import tavebalak.OTTify.community.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findByCommunityIdAndParentId(Long communityId, Long parentId);

    List<Reply> findByIdAndParentId(Long id, Long parentId);

    boolean existsByIdAndParentId(Long id, Long parentId);

    Optional<Reply> findByCommunityIdAndIdAndParentIdIsNotNull(Long communityId, Long id);

    Optional<Reply> findByCommunityIdAndIdAndParentIdIsNull(Long communityId, Long id);

    @Query("select distinct r.community from Reply r join fetch r.community.program where r.user.id =:userId order by r.community.createdAt")
    Slice<Community> findAllCommunityByUserId(@Param("userId") Long userId, Pageable pageable);

    List<Reply> findByCommunityId(Long communityId);
}
