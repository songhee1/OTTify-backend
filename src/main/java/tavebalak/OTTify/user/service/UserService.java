package tavebalak.OTTify.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tavebalak.OTTify.error.ErrorCode;
import tavebalak.OTTify.error.exception.NotFoundException;
import tavebalak.OTTify.genre.dto.FirstGenreUpdateRequestDTO;
import tavebalak.OTTify.genre.entity.Genre;
import tavebalak.OTTify.genre.entity.UserGenre;
import tavebalak.OTTify.genre.repository.GenreRepository;
import tavebalak.OTTify.genre.repository.UserGenreRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserGenreRepository userGenreRepository;
    private final GenreRepository genreRepository;

    @Transactional
    public Long update1stLikedGenre(Long userId, FirstGenreUpdateRequestDTO updateRequestDTO) {
        UserGenre userGenre = userGenreRepository.find1stGenreByUserId(userId);
        Genre genre = genreRepository.findById(updateRequestDTO.getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.GENRE_NOT_FOUND));

        userGenre.change1stGenre(genre);

        return userId;
    }
}
