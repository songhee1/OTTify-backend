package tavebalak.OTTify.program.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tavebalak.OTTify.common.BaseResponse;
import tavebalak.OTTify.error.ErrorCode;
import tavebalak.OTTify.error.exception.UnauthorizedException;
import tavebalak.OTTify.oauth.jwt.SecurityUtil;
import tavebalak.OTTify.program.service.ProgramChoiceService;
import tavebalak.OTTify.user.entity.User;
import tavebalak.OTTify.user.repository.UserRepository;

@RestController
@RequiredArgsConstructor
@Api(tags = {"프로그램 좋아요 및 관심없어요 지정"})

public class ProgramChoiceController {

    private final UserRepository userRepository;
    private final ProgramChoiceService programChoiceService;


    @ApiOperation(value = "프로그램 좋아요 지정 및 취소", notes = "프로그램 좋아요를 지정하고 한번 더 호출하면 취소합니다.")
    @ApiResponse(code = 200, message = "프로그램 좋아요 및 해제 실행 완료")
    @ApiImplicitParam(name = "programId", dataType = "long", value = "프로그램 ID 값", required = true, paramType = "path", example = "1")
    @PostMapping("/api/v1/program/like/{programId}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse likeProgram(@PathVariable("programId") Long programId) {
        User findUser = getUser();
        programChoiceService.LikeProgram(findUser, programId);
        return BaseResponse.success("프로그램 좋아요 및 해제 실행 완료 ");
    }

    @ApiOperation(value = "프로그램 관심없어요 지정 및 취소", notes = "프로그램 관심없어요를 지정하고 한번 더 호출하면 취소합니다")
    @ApiResponse(code = 200, message = "프로그램 관심없어요 및 해제 실행 완료")
    @ApiImplicitParam(name = "programId", dataType = "long", value = "프로그램 ID 값", required = true, paramType = "path", example = "1")
    @PostMapping("/api/v1/program/uninterested/{programId}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse unInterestedProgram(@PathVariable("programId") Long programId) {
        User findUser = getUser();
        programChoiceService.UnInterestedProgram(findUser, programId);
        return BaseResponse.success("프로그램 관심없어요 및 해제 실행 완료");
    }


    private User getUser() {
        return userRepository.findByEmail(SecurityUtil.getCurrentEmail().get())
            .orElseThrow(() -> new UnauthorizedException(ErrorCode.UNAUTHORIZED));
    }
}
