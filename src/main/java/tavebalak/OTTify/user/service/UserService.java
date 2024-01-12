package tavebalak.OTTify.user.service;

import tavebalak.OTTify.user.dto.Request.UserOttUpdateDTO;
import tavebalak.OTTify.user.dto.Request.UserProfileUpdateDTO;
import tavebalak.OTTify.user.dto.Response.UserOttListDTO;
import tavebalak.OTTify.user.dto.Response.UserProfileDTO;
import tavebalak.OTTify.genre.dto.request.GenreUpdateDTO;

import java.util.List;

public interface UserService {
    UserProfileDTO getUserProfile(Long userId);
    UserOttListDTO getUserOTT(Long userId);
    Long updateUserProfile(Long userId, UserProfileUpdateDTO updateRequestDTO);
    Long updateUserOTT(Long userId, List<UserOttUpdateDTO> updateRequestDTO);
    void update1stGenre(Long userId, GenreUpdateDTO updateRequestDTO);
    void update2ndGenre(Long userId, GenreUpdateDTO updateRequestDTO);
}
