package tavebalak.OTTify.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tavebalak.OTTify.common.BaseResponse;
import tavebalak.OTTify.error.exception.NotFoundException;
import tavebalak.OTTify.user.dto.Response.UserOttDTO;
import tavebalak.OTTify.user.dto.Request.UserOttUpdateDTO;
import tavebalak.OTTify.user.dto.Response.UserProfileDTO;
import tavebalak.OTTify.user.dto.Request.UserProfileUpdateDTO;
import tavebalak.OTTify.user.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<UserProfileDTO> getUserProfile(@PathVariable("id") Long userId) throws NotFoundException {
        UserProfileDTO userProfileResponseDTO = userService.getUserProfile(userId);
        return BaseResponse.success(userProfileResponseDTO);
    }

    @GetMapping("/{id}/otts")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<UserOttDTO>> getUserOTT(@PathVariable("id") Long userId) throws NotFoundException {
        List<UserOttDTO> userOttResponseDTO = userService.getUserOTT(userId);
        return BaseResponse.success(userOttResponseDTO);
    }

    @PatchMapping("/{id}/profile")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Long> updateUserProfile(@PathVariable("id") Long userId, @Validated @RequestBody UserProfileUpdateDTO updateRequestDTO) throws NotFoundException {
        return BaseResponse.success(userService.updateUserProfile(userId, updateRequestDTO));
    }

    @PatchMapping("/{id}/otts")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Long> updateUserOTT(@PathVariable("id") Long userId, @RequestBody List<UserOttUpdateDTO> updateRequestDTO) {
        return BaseResponse.success(userService.updateUserOTT(userId, updateRequestDTO));
    }
}
