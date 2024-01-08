package tavebalak.OTTify.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tavebalak.OTTify.common.BaseResponse;
import tavebalak.OTTify.genre.dto.request.GenreUpdateDTO;
import tavebalak.OTTify.genre.dto.request.SecondGenreUpdateRequestDTO;
import tavebalak.OTTify.user.service.UserServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    private final UserServiceImpl userService;

    @PatchMapping("/{id}/1stGenre")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Long> update1stLikedGenre(@PathVariable("id") Long userId, @Validated @RequestBody GenreUpdateDTO updateRequestDto) {
        return BaseResponse.success(userService.update1stGenre(userId, updateRequestDto));
    }

    @PatchMapping("/{id}/2ndGenre")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Long> update2ndLikedGenre(@PathVariable("id") Long userId, @Validated @RequestBody SecondGenreUpdateRequestDTO updateRequestDTO) {
        return BaseResponse.success(userService.update2ndGenre(userId, updateRequestDTO));
    }
}
