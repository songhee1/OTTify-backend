package tavebalak.OTTify.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tavebalak.OTTify.community.entity.Community;

public interface CommunityRepository extends JpaRepository<Community, Long> {
}
