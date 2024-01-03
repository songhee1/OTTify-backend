package tavebalak.OTTify.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tavebalak.OTTify.community.entity.Community;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    List<Community> findByUserIdOrderByCreatedAt(Long userId);
}
