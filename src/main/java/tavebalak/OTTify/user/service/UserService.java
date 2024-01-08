package tavebalak.OTTify.user.service;

import tavebalak.OTTify.genre.dto.FirstGenreUpdateRequestDTO;
import tavebalak.OTTify.genre.dto.SecondGenreUpdateRequestDTO;

import java.util.List;

public interface UserService {

    Long update1stLikedGenre(Long userId, FirstGenreUpdateRequestDTO updateRequestDTO);
    Long update2ndLikedGenre(Long userId, List<SecondGenreUpdateRequestDTO> updateRequestDTO);
}
