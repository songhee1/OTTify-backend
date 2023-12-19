package tavebalak.OTTify.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tavebalak.OTTify.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
