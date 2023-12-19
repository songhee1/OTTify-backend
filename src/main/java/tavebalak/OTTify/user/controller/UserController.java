package tavebalak.OTTify.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tavebalak.OTTify.common.ApiResponse;
import tavebalak.OTTify.exception.NotFoundException;
import tavebalak.OTTify.user.dto.UserProfileResponseDTO;
import tavebalak.OTTify.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<UserProfileResponseDTO> getUserProfile(@PathVariable("id") Long userId) throws NotFoundException {
        UserProfileResponseDTO userProfileResponseDTO = userService.getUserProfile(userId);
        return ApiResponse.success(userProfileResponseDTO);
    }
}
