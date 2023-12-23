package tavebalak.OTTify.oauth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import tavebalak.OTTify.common.BaseResponse;
import tavebalak.OTTify.error.ErrorCode;
import tavebalak.OTTify.error.exception.UnauthorizedException;
import tavebalak.OTTify.oauth.jwt.JwtService;
import tavebalak.OTTify.oauth.jwt.SecurityUtil;
import tavebalak.OTTify.oauth.jwt.TokenDto;
import tavebalak.OTTify.oauth.redis.RefreshToken;
import tavebalak.OTTify.oauth.redis.RefreshTokenRepository;
import tavebalak.OTTify.oauth.redis.RefreshTokenService;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final RefreshTokenRepository tokenRepository;
    private final RefreshTokenService tokenService;
    private final JwtService jwtService;

//    @PostMapping("token/logout")
//    public ResponseEntity<StatusResponseDto> logout(@RequestHeader("Authorization") final String accessToken) {
//
//        // 엑세스 토큰으로 현재 Redis 정보 삭제
//        tokenService.removeRefreshToken(accessToken);
//        return ResponseEntity.ok(StatusResponseDto.addStatus(200));
//    }

    @PostMapping("/token/refresh")
    public BaseResponse<TokenDto> refresh(@RequestHeader("Authorization-Refresh") final String refreshToken) {

        // 액세스 토큰으로 Refresh 토큰 객체를 조회
        Optional<RefreshToken> token = tokenRepository.findByRefreshToken(refreshToken.replace("Bearer ", ""));
        if(token.isEmpty())
            throw new UnauthorizedException(ErrorCode.REFRESHTOKEN_NOT_FOUND);
        String email = token.get().getEmail();
//        tokenService.removeRefreshToken(refreshToken);
        String newAccessToken = jwtService.createAccessToken(email);
        String newRefreshToken = jwtService.createRefreshToken();
        jwtService.updateRefreshToken(email, newRefreshToken);
//        tokenService.saveTokenInfo(email, refreshToken);
        log.info(tokenRepository.findByRefreshToken(newRefreshToken).get().getEmail());
        return BaseResponse.success(TokenDto.builder().accessToken(newAccessToken).refreshToken(newRefreshToken).build());

    }

    /*
    밑 코드는
    테스트 용 지워도 됨
     */
    @GetMapping("/hello")
    public BaseResponse<String> dd(){
        return BaseResponse.success(SecurityUtil.getCurrentEmail().get());
    }

}