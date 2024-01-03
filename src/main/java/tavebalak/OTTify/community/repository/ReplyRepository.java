package tavebalak.OTTify.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tavebalak.OTTify.community.entity.Community;
import tavebalak.OTTify.community.entity.Reply;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query("select distinct r.community from Reply r where r.user.id =:userId order by r.community.createdAt")
    List<Community> findAllCommunityByUserId(Long userId);
}
