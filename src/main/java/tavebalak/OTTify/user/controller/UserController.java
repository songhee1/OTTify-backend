package tavebalak.OTTify.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tavebalak.OTTify.common.BaseResponse;
import tavebalak.OTTify.community.dto.MyDiscussionDto;
import tavebalak.OTTify.review.dto.MyReviewDto;
import tavebalak.OTTify.user.Service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    private final UserService userService;

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
