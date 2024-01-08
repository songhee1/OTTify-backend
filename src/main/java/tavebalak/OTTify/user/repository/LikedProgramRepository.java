package tavebalak.OTTify.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tavebalak.OTTify.user.entity.LikedProgram;

import java.util.List;

public interface LikedProgramRepository extends JpaRepository<LikedProgram, Long> {
    public List<LikedProgram> findByUserId(Long userId);
}
