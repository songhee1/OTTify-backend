package tavebalak.OTTify.oauth.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repository;

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

    @Transactional
    public void removeByEmail(String email) {
        RefreshToken token = repository.findByEmail(email)
                .orElseThrow(IllegalArgumentException::new);

        repository.delete(token);
    }
}