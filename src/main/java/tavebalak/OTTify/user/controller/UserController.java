package tavebalak.OTTify.user.controller;

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
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<UserProfileDTO> getUserProfile(@PathVariable("id") Long userId) {
        return BaseResponse.success(userService.getUserProfile(userId));
    }

    @GetMapping("/{id}/otts")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<UserOttListDTO> getUserOTT(@PathVariable("id") Long userId) {
        return BaseResponse.success(userService.getUserOTT(userId));
    }

    @PatchMapping("/{id}/profile")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Long> updateUserProfile(@PathVariable("id") Long userId, @Validated @RequestBody UserProfileUpdateDTO updateRequestDTO) {
        return BaseResponse.success(userService.updateUserProfile(userId, updateRequestDTO));
    }

    @PatchMapping("/{id}/otts")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Long> updateUserOTT(@PathVariable("id") Long userId, @RequestBody List<UserOttUpdateDTO> updateRequestDTO) {
        return BaseResponse.success(userService.updateUserOTT(userId, updateRequestDTO));
    }

    @PatchMapping("/{id}/1stGenre")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse update1stLikedGenre(@PathVariable("id") Long userId, @Validated @RequestBody GenreUpdateDTO updateRequestDto) {
        userService.update1stGenre(userId, updateRequestDto);
        return BaseResponse.success("성공적으로 1순위 장르가 업데이트 되었습니다.");
    }

    @PatchMapping("/{id}/2ndGenre")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse update2ndLikedGenre(@PathVariable("id") Long userId, @Validated @RequestBody GenreUpdateDTO updateRequestDTO) {
        userService.update2ndGenre(userId, updateRequestDTO);
        return BaseResponse.success("성공적으로 2순위 장르가 업데이트 되었습니다.");
    }

    @GetMapping("/{id}/reviews")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<MyReviewDto>> getMyReview(@PathVariable("id") Long userId, @PageableDefault(
            size = 5,
            sort = "createdAt",
            direction = Sort.Direction.DESC,
            page = 0) Pageable pageable) {
        return BaseResponse.success(userService.getMyReview(userId, pageable));
    }

    @GetMapping("/{id}/likedReviews")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<MyReviewDto>> getLikedReview(@PathVariable("id") Long userId, @PageableDefault(
            size = 5,
            page = 0) Pageable pageable) {
        return BaseResponse.success(userService.getLikedReview(userId, pageable));
    }

    @GetMapping("/{id}/discussion/hosting")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<MyDiscussionDto>> getHostedDiscussion(@PathVariable("id") Long userId, @PageableDefault(
            size = 5,
            sort = "createdAt",
            direction = Sort.Direction.DESC,
            page = 0) Pageable pageable) {
        return BaseResponse.success(userService.getHostedDiscussion(userId, pageable));
    }

    @GetMapping("/{id}/discussion/participating")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<MyDiscussionDto>> getParticipatedDiscussion(@PathVariable("id") Long userId, @PageableDefault(
            size = 5,
            page = 0) Pageable pageable) {
        return BaseResponse.success(userService.getParticipatedDiscussion(userId, pageable));
    }
}
