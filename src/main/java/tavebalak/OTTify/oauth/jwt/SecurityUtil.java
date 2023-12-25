package tavebalak.OTTify.oauth.jwt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import tavebalak.OTTify.oauth.SecurityUserDto;

import java.util.Optional;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtil {

    /**
     * Header로 부터 email 추출
     * !! 주의 !!
     * 헤더는 무조건 Bearer 토큰 형식으로 이루어져야 작동함
     */
    public static Optional<String> getCurrentEmail() {
        // Security Context에 Authentication 객체가 저장되는 시점은
        // JwtFilter의 doFilter메소드에서 Request가 들어올 때 SecurityContext에 Authentication 객체를 저장해서 사용
        log.info("accessToken에서 email 가져오기");
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.debug("Security Context에 인증 정보가 없습니다.");
            return Optional.empty();
        }

        String email = null;
        log.info(authentication.getPrincipal().toString());
        if (authentication.getPrincipal() instanceof SecurityUserDto) {
            SecurityUserDto springSecurityUser = (SecurityUserDto) authentication.getPrincipal();
            email = springSecurityUser.getEmail();
        } else if (authentication.getPrincipal() instanceof String) {
            email = (String) authentication.getPrincipal();
        }

        return Optional.ofNullable(email);
    }
}