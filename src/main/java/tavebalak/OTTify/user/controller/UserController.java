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
    @ApiImplicitParam(name = "userId", dataType = "long", value = "유저 id", required = true, paramType = "path")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<UserProfileDTO> getUserProfile(@PathVariable("id") Long userId) {
        return BaseResponse.success(userService.getUserProfile(userId));
    }

    @ApiOperation(value = "구독 중인 OTT 조회 api", notes = "유저가 구독 중인 OTT를 조회합니다.")
    @ApiImplicitParam(name = "userId", dataType = "long", value = "유저 id", required = true, paramType = "path")
    @GetMapping("/{id}/otts")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<UserOttListDTO> getUserOTT(@PathVariable("id") Long userId) {
        return BaseResponse.success(userService.getUserOTT(userId));
    }

    @ApiOperation(value = "프로필 수정 api", notes = "유저 프로필(닉네임, 프로필 사진)을 수정합니다.")
    @ApiImplicitParam(name = "userId", dataType = "long", value = "유저 id", required = true, paramType = "path")
    @ApiResponse(code = 200, message = "성공적으로 프로필이 업데이트 되었습니다.")
    @PatchMapping("/{id}/profile")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Long> updateUserProfile(@PathVariable("id") Long userId, @Validated @RequestBody UserProfileUpdateDTO updateRequestDTO) {
        return BaseResponse.success(userService.updateUserProfile(userId, updateRequestDTO));
    }

    @ApiOperation(value = "구독 중인 OTT 수정 api", notes = "유저가 구독 중인 OTT를 수정합니다.")
    @ApiImplicitParam(name = "userId", dataType = "long", value = "유저 id", required = true, paramType = "path")
    @ApiResponse(code = 200, message = "성공적으로 구독 중인 OTT가 업데이트 되었습니다.")
    @PatchMapping("/{id}/otts")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Long> updateUserOTT(@PathVariable("id") Long userId, @RequestBody List<UserOttUpdateDTO> updateRequestDTO) {
        return BaseResponse.success(userService.updateUserOTT(userId, updateRequestDTO));
    }

    @ApiOperation(value = "1순위 취향 장르 수정 api", notes = "유저의 1순위 취향 장르를 수정합니다.")
    @ApiImplicitParam(name = "userId", dataType = "long", value = "유저 id", required = true, paramType = "path")
    @ApiResponse(code = 200, message = "성공적으로 1순위 장르가 업데이트 되었습니다.")
    @PatchMapping("/{id}/1stGenre")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse update1stLikedGenre(@PathVariable("id") Long userId, @Validated @RequestBody GenreUpdateDTO updateRequestDto) {
        userService.update1stGenre(userId, updateRequestDto);
        return BaseResponse.success("성공적으로 1순위 장르가 업데이트 되었습니다.");
    }

    @ApiOperation(value = "2순위 취향 장르 수정 api", notes = "유저의 2순위 취향 장르를 수정합니다.")
    @ApiImplicitParam(name = "userId", dataType = "long", value = "유저 id", required = true, paramType = "path")
    @ApiResponse(code = 200, message = "성공적으로 2순위 장르가 업데이트 되었습니다.")
    @PatchMapping("/{id}/2ndGenre")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse update2ndLikedGenre(@PathVariable("id") Long userId, @Validated @RequestBody GenreUpdateDTO updateRequestDTO) {
        userService.update2ndGenre(userId, updateRequestDTO);
        return BaseResponse.success("성공적으로 2순위 장르가 업데이트 되었습니다.");
    }

    @ApiOperation(value = "작성 리뷰 조회 api", notes = "유저가 작성한 리뷰를 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", dataType = "long", value = "유저 id", required = true, paramType = "path"),
            @ApiImplicitParam(name = "page", dataType = "int", value = "페이지 번호(0부터 시작)", required = false, paramType = "path"),
            @ApiImplicitParam(name = "direction", dataType = "String", value = "내림차순과 오름차순", required = false, paramType = "path"),
            @ApiImplicitParam(name = "sort", dataType = "String", value = "정렬기준(createdAt, updatedAt)", required = false, paramType = "path"),
            @ApiImplicitParam(name = "size", dataType = "int", value = "페이지당 아이템 갯수", required = false, paramType = "path")
    })
    @GetMapping("/{id}/reviews")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<MyReviewDto>> getMyReview(@PathVariable("id") Long userId, @PageableDefault(
            size = 5,
            sort = "createdAt",
            direction = Sort.Direction.DESC,
            page = 0) Pageable pageable) {
        return BaseResponse.success(userService.getMyReview(userId, pageable));
    }

    @ApiOperation(value = "좋아요한 리뷰 조회 api", notes = "유저가 좋아요한 리뷰를 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", dataType = "long", value = "유저 id", required = true, paramType = "path"),
            @ApiImplicitParam(name = "page", dataType = "int", value = "페이지 번호(0부터 시작)", required = false, paramType = "path"),
            @ApiImplicitParam(name = "size", dataType = "int", value = "페이지당 아이템 갯수", required = false, paramType = "path")
    })
    @GetMapping("/{id}/likedReviews")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<MyReviewDto>> getLikedReview(@PathVariable("id") Long userId, @PageableDefault(
            size = 5,
            page = 0) Pageable pageable) {
        return BaseResponse.success(userService.getLikedReview(userId, pageable));
    }

    @ApiOperation(value = "주최한 토론 조회 api", notes = "유저가 주최한 토론을 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", dataType = "long", value = "유저 id", required = true, paramType = "path"),
            @ApiImplicitParam(name = "page", dataType = "int", value = "페이지 번호(0부터 시작)", required = false, paramType = "path"),
            @ApiImplicitParam(name = "direction", dataType = "String", value = "내림차순과 오름차순", required = false, paramType = "path"),
            @ApiImplicitParam(name = "sort", dataType = "String", value = "정렬기준(createdAt, updatedAt)", required = false, paramType = "path"),
            @ApiImplicitParam(name = "size", dataType = "int", value = "페이지당 아이템 갯수", required = false, paramType = "path")
    })
    @GetMapping("/{id}/discussion/hosting")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<MyDiscussionDto>> getHostedDiscussion(@PathVariable("id") Long userId, @PageableDefault(
            size = 5,
            sort = "createdAt",
            direction = Sort.Direction.DESC,
            page = 0) Pageable pageable) {
        return BaseResponse.success(userService.getHostedDiscussion(userId, pageable));
    }

    @ApiOperation(value = "참여한 토론 조회 api", notes = "유저가 참여한 토론을 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", dataType = "long", value = "유저 id", required = true, paramType = "path"),
            @ApiImplicitParam(name = "page", dataType = "int", value = "페이지 번호(0부터 시작)", required = false, paramType = "path"),
            @ApiImplicitParam(name = "size", dataType = "int", value = "페이지당 아이템 갯수", required = false, paramType = "path")
    })
    @GetMapping("/{id}/discussion/participating")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<MyDiscussionDto>> getParticipatedDiscussion(@PathVariable("id") Long userId, @PageableDefault(
            size = 5,
            page = 0) Pageable pageable) {
        return BaseResponse.success(userService.getParticipatedDiscussion(userId, pageable));
    }
}
