package tavebalak.OTTify.oauth.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repository;
//    private final RedisTemplate<String, String> redisTemplate;
//
//    @Transactional
//    public void saveTokenInfo(String email, String refreshToken) {
//        redisTemplate.opsForValue().set(refreshToken, email);
//    }
//
//    @Transactional
//    public void removeRefreshToken(String refreshToken) {
////        RefreshToken token = repository.findByRefreshToken(refreshToken)
////                .orElseThrow(IllegalArgumentException::new);
//
//        redisTemplate.delete(refreshToken);
//    }
//
//    public String getValues(String key){
//        return redisTemplate.opsForValue().get(key);
//    }


    @Transactional
    public void saveTokenInfo(String email, String refreshToken) {
        repository.save(new RefreshToken(refreshToken, email));
    }

    @Transactional
    public void removeRefreshToken(String refreshToken) {
        RefreshToken token = repository.findByRefreshToken(refreshToken)
                .orElseThrow(IllegalArgumentException::new);

        repository.delete(token);
    }
}