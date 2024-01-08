package tavebalak.OTTify.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tavebalak.OTTify.error.ErrorCode;
import tavebalak.OTTify.error.exception.NotFoundException;
import tavebalak.OTTify.genre.dto.request.GenreUpdateDTO;
import tavebalak.OTTify.genre.entity.Genre;
import tavebalak.OTTify.genre.entity.UserGenre;
import tavebalak.OTTify.genre.repository.GenreRepository;
import tavebalak.OTTify.genre.repository.UserGenreRepository;
import tavebalak.OTTify.user.repository.UserRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserGenreRepository userGenreRepository;
    private final GenreRepository genreRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Long update1stGenre(Long userId, GenreUpdateDTO updateRequestDTO) {
        UserGenre userGenre = userGenreRepository.findByUserIdAndIsFirst(userId, true)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        Genre genre = genreRepository.findById(updateRequestDTO.getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.GENRE_NOT_FOUND));

        userGenre.change1stGenre(genre);

        return userId;
    }

    @Override
    @Transactional
    public Long update2ndGenre(Long userId, GenreUpdateDTO updateRequestDTO) {
        // req로 들어온 id 값이 유효한 장르 id인지 확인
        Genre genre = genreRepository.findById(updateRequestDTO.getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.GENRE_NOT_FOUND));

        // genreId와 userId로 UserGenre 조회
        boolean isUserGenreExists = userGenreRepository.existsByGenreIdAndUserIdAndIsFirst(genre.getId(), userId, false);

        // 조회된 UserGenre가 없을 경우 저장 & 있을 경우 삭제
        if (isUserGenreExists) {
            UserGenre findUserGenre = userGenreRepository.findByGenreIdAndUserIdAndIsFirst(genre.getId(), userId, false);
            userGenreRepository.delete(findUserGenre);
        } else {
            userGenreRepository.save(UserGenre.builder()
                    .genre(genre)
                    .user(userRepository.findById(userId)
                            .orElseThrow(() -> new NotFoundException(ErrorCode.ENTITY_NOT_FOUND)))
                    .build());
        }

        return userId;
    }

}
