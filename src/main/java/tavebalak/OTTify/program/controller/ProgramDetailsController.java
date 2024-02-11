package tavebalak.OTTify.program.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tavebalak.OTTify.common.BaseResponse;
import tavebalak.OTTify.error.ErrorCode;
import tavebalak.OTTify.error.exception.UnauthorizedException;
import tavebalak.OTTify.oauth.jwt.SecurityUtil;
import tavebalak.OTTify.program.dto.programDetails.Response.ProgramResponseDto;
import tavebalak.OTTify.program.dto.response.UserSpecificRatingResponseDto;
import tavebalak.OTTify.program.service.ProgramDetailsShowService;
import tavebalak.OTTify.user.entity.User;
import tavebalak.OTTify.user.repository.UserRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/program/{programId}")
@Api(tags = {"프로그램에 대한 상세정보 컨트롤러"})

public class ProgramDetailsController {

    private final ProgramDetailsShowService programDetailsShowService;
    private final UserRepository userRepository;


    @ApiOperation(value = "프로그램에 대한 상세 정보 보기", notes = "프로그램에 대한 상세 정보를 받아옵니다")
    @ApiImplicitParam(name = "programId", dataType = "long", value = "프로그램 ID", required = true, paramType = "path")
    @GetMapping("/details")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ProgramResponseDto> getProgramDetails(
        @PathVariable("programId") Long programId) {
        return BaseResponse.success(programDetailsShowService.showDetails(programId));
    }

    @ApiOperation(value = "자신의 취향에 맞는 사람들의 리뷰의 평균 별점 및 현재 자신의 첫번째 장르 보여주기", notes = "자신의 취향에 맞는 사람들의 리뷰의 평균 별점, 첫번째 장르를 보여줍니다")
    @ApiImplicitParam(name = "programId", dataType = "long", value = "현재 프로그램의 ID", required = true, paramType = "path")
    @GetMapping("/user/specific/rating")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<UserSpecificRatingResponseDto> showUserSpecificReviewRating(
        @PathVariable("programId") Long programId) {
        User findUser = getUser();
        return BaseResponse.success(
            programDetailsShowService.showUserSpecificRating(findUser, programId));
    }

    private User getUser() {
        return userRepository.findByEmail(SecurityUtil.getCurrentEmail().get())
            .orElseThrow(() -> new UnauthorizedException(ErrorCode.UNAUTHORIZED));
    }
}
