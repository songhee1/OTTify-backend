package tavebalak.OTTify.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import tavebalak.OTTify.user.dto.UserOttResponseDTO;
import tavebalak.OTTify.user.entity.User;
import tavebalak.OTTify.user.entity.UserSubscribingOTT;

import java.util.List;

public interface UserSubscribingOttRepository extends JpaRepository<UserSubscribingOTT, Long> {

    List<UserSubscribingOTT> findUserSubscribingOTTByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("delete from UserSubscribingOTT uso where uso.ott.id in :ottIds and uso.user.id =:userId")
    void deleteAllByIdInQuery(@Param("ottIds") List<Long> ottIds, @Param("userId") Long userId);
}
