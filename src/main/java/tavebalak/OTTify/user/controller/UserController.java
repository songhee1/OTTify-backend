package tavebalak.OTTify.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tavebalak.OTTify.common.BaseResponse;
import tavebalak.OTTify.genre.dto.FirstGenreUpdateRequestDTO;
import tavebalak.OTTify.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @PatchMapping("/{id}/1stLikedGenre")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Long> update1stLikedGenre(@PathVariable("id") Long userId, @Validated @RequestBody FirstGenreUpdateRequestDTO updateRequestDto) {
        return BaseResponse.success(userService.update1stLikedGenre(userId, updateRequestDto));
    }
}
