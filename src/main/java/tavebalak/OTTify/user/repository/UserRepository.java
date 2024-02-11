package tavebalak.OTTify.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tavebalak.OTTify.common.constant.SocialType;
import tavebalak.OTTify.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndSocialType(String email, SocialType socialType);

    Optional<User> findByEmail(String email);

    Boolean existsByNickName(String nickName);
}
