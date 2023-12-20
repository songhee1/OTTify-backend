package tavebalak.OTTify.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tavebalak.OTTify.user.dto.UserOttResponseDTO;
import tavebalak.OTTify.user.entity.UserSubscribingOTT;

import java.util.List;

public interface UserSubscribingOttRepository extends JpaRepository<UserSubscribingOTT, Long> {

    @Query("select new tavebalak.OTTify.user.dto.UserOttResponseDTO(uso.ott.id, uso.ott.logoPath) from UserSubscribingOTT uso where uso.user.id =:userId")
    List<UserOttResponseDTO> findUserSubscribingOTT(@Param("userId") Long userId);
}
