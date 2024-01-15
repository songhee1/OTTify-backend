package tavebalak.OTTify.user.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tavebalak.OTTify.common.BaseResponse;
import tavebalak.OTTify.user.service.UserService;
import tavebalak.OTTify.user.dto.Request.UserOttUpdateDTO;
import tavebalak.OTTify.user.dto.Response.UserOttListDTO;
import tavebalak.OTTify.user.dto.Response.UserProfileDTO;
import tavebalak.OTTify.user.dto.Request.UserProfileUpdateDTO;
import tavebalak.OTTify.genre.dto.request.GenreUpdateDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import tavebalak.OTTify.community.dto.response.MyDiscussionDto;
import tavebalak.OTTify.review.dto.response.MyReviewDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Slf4j
@Api(tags = {"마이페이지 컨트롤러"})
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "프로필 조회 api", notes = "유저 프로필(닉네임, 프로필 사진)을 조회합니다.")
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<UserProfileDTO> getUserProfile() {
        return BaseResponse.success(userService.getUserProfile());
    }

    @ApiOperation(value = "구독 중인 OTT 조회 api", notes = "유저가 구독 중인 OTT를 조회합니다.")
    @GetMapping("/otts")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<UserOttListDTO> getUserOTT() {
        return BaseResponse.success(userService.getUserOTT());
    }

    @ApiOperation(value = "프로필 수정 api", notes = "유저 프로필(닉네임, 프로필 사진)을 수정합니다.")
    @ApiResponse(code = 200, message = "성공적으로 프로필이 업데이트 되었습니다.")
    @PatchMapping("/profile")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse updateUserProfile(@Validated @RequestBody UserProfileUpdateDTO updateRequestDTO) {
        userService.updateUserProfile(updateRequestDTO);
        return BaseResponse.success("성공적으로 프로필이 업데이트 되었습니다.");
    }

    @ApiOperation(value = "구독 중인 OTT 수정 api", notes = "유저가 구독 중인 OTT를 수정합니다.")
    @ApiResponse(code = 200, message = "성공적으로 구독 중인 OTT가 업데이트 되었습니다.")
    @PatchMapping("/otts")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse updateUserOTT(@RequestBody List<UserOttUpdateDTO> updateRequestDTO) {
        userService.updateUserOTT(updateRequestDTO);
        return BaseResponse.success("성공적으로 구독 중인 OTT가 업데이트 되었습니다.");
    }

    @ApiOperation(value = "1순위 취향 장르 수정 api", notes = "유저의 1순위 취향 장르를 수정합니다.")
    @ApiResponse(code = 200, message = "성공적으로 1순위 장르가 업데이트 되었습니다.")
    @PatchMapping("/1stGenre")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse update1stLikedGenre(@Validated @RequestBody GenreUpdateDTO updateRequestDto) {
        userService.update1stGenre(updateRequestDto);
        return BaseResponse.success("성공적으로 1순위 장르가 업데이트 되었습니다.");
    }

    @ApiOperation(value = "2순위 취향 장르 수정 api", notes = "유저의 2순위 취향 장르를 수정합니다.")
    @ApiResponse(code = 200, message = "성공적으로 2순위 장르가 업데이트 되었습니다.")
    @PatchMapping("/2ndGenre")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse update2ndLikedGenre(@Validated @RequestBody GenreUpdateDTO updateRequestDTO) {
        userService.update2ndGenre(updateRequestDTO);
        return BaseResponse.success("성공적으로 2순위 장르가 업데이트 되었습니다.");
    }

    @ApiOperation(value = "작성 리뷰 조회 api", notes = "유저가 작성한 리뷰를 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", value = "페이지 번호(0부터 시작)", required = false, paramType = "path"),
            @ApiImplicitParam(name = "direction", dataType = "String", value = "내림차순과 오름차순", required = false, paramType = "path"),
            @ApiImplicitParam(name = "sort", dataType = "String", value = "정렬기준(createdAt, updatedAt)", required = false, paramType = "path"),
            @ApiImplicitParam(name = "size", dataType = "int", value = "페이지당 아이템 갯수", required = false, paramType = "path")
    })
    @GetMapping("/reviews")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<MyReviewDto>> getMyReview(@PageableDefault(
            size = 5,
            sort = "createdAt",
            direction = Sort.Direction.DESC,
            page = 0) Pageable pageable) {
        return BaseResponse.success(userService.getMyReview(pageable));
    }

    @ApiOperation(value = "좋아요한 리뷰 조회 api", notes = "유저가 좋아요한 리뷰를 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", value = "페이지 번호(0부터 시작)", required = false, paramType = "path"),
            @ApiImplicitParam(name = "size", dataType = "int", value = "페이지당 아이템 갯수", required = false, paramType = "path")
    })
    @GetMapping("/likedReviews")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<MyReviewDto>> getLikedReview(@PageableDefault(
            size = 5,
            page = 0) Pageable pageable) {
        return BaseResponse.success(userService.getLikedReview(pageable));
    }

    @ApiOperation(value = "주최한 토론 조회 api", notes = "유저가 주최한 토론을 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", value = "페이지 번호(0부터 시작)", required = false, paramType = "path"),
            @ApiImplicitParam(name = "direction", dataType = "String", value = "내림차순과 오름차순", required = false, paramType = "path"),
            @ApiImplicitParam(name = "sort", dataType = "String", value = "정렬기준(createdAt, updatedAt)", required = false, paramType = "path"),
            @ApiImplicitParam(name = "size", dataType = "int", value = "페이지당 아이템 갯수", required = false, paramType = "path")
    })
    @GetMapping("/discussion/hosting")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<MyDiscussionDto>> getHostedDiscussion(@PageableDefault(
            size = 5,
            sort = "createdAt",
            direction = Sort.Direction.DESC,
            page = 0) Pageable pageable) {
        return BaseResponse.success(userService.getHostedDiscussion(pageable));
    }

    @ApiOperation(value = "참여한 토론 조회 api", notes = "유저가 참여한 토론을 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", value = "페이지 번호(0부터 시작)", required = false, paramType = "path"),
            @ApiImplicitParam(name = "size", dataType = "int", value = "페이지당 아이템 갯수", required = false, paramType = "path")
    })
    @GetMapping("/discussion/participating")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<MyDiscussionDto>> getParticipatedDiscussion(@PageableDefault(
            size = 5,
            page = 0) Pageable pageable) {
        return BaseResponse.success(userService.getParticipatedDiscussion(pageable));
    }
}
