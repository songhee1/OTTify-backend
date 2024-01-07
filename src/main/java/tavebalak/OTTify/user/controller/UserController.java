package tavebalak.OTTify.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tavebalak.OTTify.common.BaseResponse;
import tavebalak.OTTify.error.exception.NotFoundException;
import tavebalak.OTTify.user.dto.UserOttResponseDTO;
import tavebalak.OTTify.user.dto.UserOttUpdateRequestDTO;
import tavebalak.OTTify.user.dto.UserProfileResponseDTO;
import tavebalak.OTTify.user.dto.UserProfileUpdateRequestDTO;
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
    public BaseResponse<UserProfileResponseDTO> getUserProfile(@PathVariable("id") Long userId) throws NotFoundException {
        UserProfileResponseDTO userProfileResponseDTO = userService.getUserProfile(userId);
        return BaseResponse.success(userProfileResponseDTO);
    }

    @GetMapping("/{id}/otts")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<UserOttResponseDTO>> getUserOTT(@PathVariable("id") Long userId) throws NotFoundException {
        List<UserOttResponseDTO> userOttResponseDTO = userService.getUserOTT(userId);
        return BaseResponse.success(userOttResponseDTO);
    }

    @PatchMapping("/{id}/profile")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Long> updateUserProfile(@PathVariable("id") Long userId, @Validated @RequestBody UserProfileUpdateRequestDTO updateRequestDTO) throws NotFoundException {
        return BaseResponse.success(userService.updateUserProfile(userId, updateRequestDTO));
    }

    @PatchMapping("/{id}/otts")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Long> updateUserOTT(@PathVariable("id") Long userId, @RequestBody List<UserOttUpdateRequestDTO> updateRequestDTO) {
        return BaseResponse.success(userService.updateUserOTT(userId, updateRequestDTO));
    }
}
