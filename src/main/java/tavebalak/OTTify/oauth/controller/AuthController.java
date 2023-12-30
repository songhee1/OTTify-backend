package tavebalak.OTTify.oauth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tavebalak.OTTify.common.BaseResponse;
import tavebalak.OTTify.error.ErrorCode;
import tavebalak.OTTify.error.exception.BadRequestException;
import tavebalak.OTTify.error.exception.NotFoundException;
import tavebalak.OTTify.error.exception.UnauthorizedException;
import tavebalak.OTTify.oauth.dto.SignUpInfoDto;
import tavebalak.OTTify.oauth.jwt.JwtService;
import tavebalak.OTTify.oauth.jwt.TokenDto;
import tavebalak.OTTify.oauth.redis.RefreshToken;
import tavebalak.OTTify.oauth.redis.RefreshTokenRepository;
import tavebalak.OTTify.oauth.redis.RefreshTokenService;
import tavebalak.OTTify.oauth.service.OauthService;
import tavebalak.OTTify.user.entity.User;
import tavebalak.OTTify.user.repository.UserRepository;

import javax.validation.Valid;
import java.util.Optional;

import static tavebalak.OTTify.common.BaseResponse.success;
import static tavebalak.OTTify.error.ErrorCode.*;
import static tavebalak.OTTify.oauth.jwt.SecurityUtil.getCurrentEmail;


@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final RefreshTokenRepository tokenRepository;
    private final RefreshTokenService tokenService;
    private final JwtService jwtService;
    private final OauthService oauthService;
    private final UserRepository userRepository;

    @PostMapping("/api/v1/oauth/info")
    public BaseResponse<String> saveSignUpInformation(@Valid @RequestBody SignUpInfoDto signUpInfoDto){
        String email = getEmail();
        User user = getUserByEmail(email);
        return success(oauthService.saveInformation(user, signUpInfoDto));
    }

    @PostMapping("/api/v1/logout")
    public BaseResponse<String> logout(@RequestHeader("Authorization") String accessToken) {
        log.info("========logout 진입=========");
        String email = getEmail();
        User user = getUserByEmail(email);
        oauthService.logout(user, accessToken);
        return success("로그아웃 성공");
    }

    @DeleteMapping("/api/v1/withdrawal")
    public BaseResponse<String> withdrawal(@RequestHeader("Authorization") String accessToken) {
        log.info("========logout 진입=========");
        String email = getEmail();
        User user = getUserByEmail(email);
        oauthService.withdrawal(user, accessToken);
        return success("회원탈퇴 성공");
    }

    @PostMapping("/token/refresh")
    public BaseResponse<TokenDto> refresh(@RequestHeader("Authorization-Refresh") String refreshToken) {

        log.info(refreshToken);
        // 액세스 토큰으로 Refresh 토큰 객체를 조회
        Optional<RefreshToken> token = tokenRepository.findByRefreshToken(refreshToken.replace("Bearer ", ""));
        if(token.isEmpty())
            throw new UnauthorizedException(ErrorCode.REFRESHTOKEN_NOT_FOUND);
        String email = token.get().getEmail();
        String newAccessToken = jwtService.createAccessToken(email);
        String newRefreshToken = jwtService.createRefreshToken();
        jwtService.updateRefreshToken(email, newRefreshToken);
        log.info(tokenRepository.findByRefreshToken(newRefreshToken).get().getEmail());
        return success(TokenDto.builder().accessToken(newAccessToken).refreshToken(newRefreshToken).build());

    }

    /**
     * 밑의 두 함수는 service test 편하게 하기위해 빼놓은 것으로
     * 더 좋은 방법 있다면 수정해주시면 좋을거 같아요
     */
    private String getEmail()  {
        Optional<String> opt = getCurrentEmail();
        if(opt.isPresent()) return opt.get();
        throw new BadRequestException(EMAIL_ISNOT_EXIST);
    }

    private User getUserByEmail(String email){
        Optional<User> opt = userRepository.findByEmail(email);
        if(opt.isPresent()) return opt.get();
        throw new NotFoundException(USER_NOT_FOUND);
    }
}