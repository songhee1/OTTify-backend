package tavebalak.OTTify.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tavebalak.OTTify.user.entity.UserSubscribingOTT;

import java.util.List;

@Repository
public interface UserSubscribingOTTRepository extends JpaRepository<UserSubscribingOTT, Long> {
    List<UserSubscribingOTT> findUserSubscribingOTTByUserId(Long userId);
}
