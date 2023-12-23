package tavebalak.OTTify.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tavebalak.OTTify.oauth.constant.SocialType;
import tavebalak.OTTify.user.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
<<<<<<< HEAD
    Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String socialId);
=======
    Optional<User> findByEmailAndSocialType(String email, SocialType socialType);

    Optional<User> findByEmail(String email);
>>>>>>> 6424b81 (Feat : jwtToken 발급 및 redis 저장 (#6))
}
