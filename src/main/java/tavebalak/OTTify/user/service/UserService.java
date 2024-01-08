package tavebalak.OTTify.user.service;

import tavebalak.OTTify.genre.dto.request.GenreUpdateDTO;

public interface UserService {

    Long update1stGenre(Long userId, GenreUpdateDTO updateRequestDTO);
    Long update2ndGenre(Long userId, GenreUpdateDTO updateRequestDTO);
}
