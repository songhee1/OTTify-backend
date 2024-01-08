package tavebalak.OTTify.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tavebalak.OTTify.error.ErrorCode;
import tavebalak.OTTify.error.exception.NotFoundException;
import tavebalak.OTTify.genre.dto.FirstGenreUpdateRequestDTO;
import tavebalak.OTTify.genre.dto.SecondGenreUpdateRequestDTO;
import tavebalak.OTTify.genre.entity.Genre;
import tavebalak.OTTify.genre.entity.UserGenre;
import tavebalak.OTTify.genre.repository.GenreRepository;
import tavebalak.OTTify.genre.repository.UserGenreRepository;
import tavebalak.OTTify.user.entity.User;
import tavebalak.OTTify.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

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
    public Long update1stLikedGenre(Long userId, FirstGenreUpdateRequestDTO updateRequestDTO) {
        UserGenre userGenre = userGenreRepository.find1stGenreByUserId(userId);
        Genre genre = genreRepository.findById(updateRequestDTO.getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.GENRE_NOT_FOUND));

        userGenre.change1stGenre(genre);

        return userId;
    }

    @Override
    @Transactional
    public Long update2ndLikedGenre(Long userId, List<SecondGenreUpdateRequestDTO> updateRequestDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        // 이전에 선택한 2순위 장르 리스트
        List<Long> preGenreList = userGenreRepository.find2ndGenreByUserId(userId).stream()
                .map((UserGenre ug) -> ug.getGenre().getId())
                .collect(Collectors.toList());
        System.out.println("preGenreList = " + preGenreList);

        // 현재 선택한 2순위 장르 리스트
        List<Long> nowGenreList = updateRequestDTO.stream()
                .map(SecondGenreUpdateRequestDTO::getId)
                .collect(Collectors.toList());
        System.out.println("nowGenreList = " + nowGenreList);

        if (!preGenreList.isEmpty()) { // 이전 선택한 2순위 장르가 있는 경우
            // 삭제할 장르 리스트 - 이전 리스트에는 있는데 현재 리스트에는 없는 장르
            List<Long> deleteGenres = preGenreList.stream()
                    .filter(g -> !nowGenreList.contains(g))
                    .collect(Collectors.toList());
            System.out.println("deleteGenres = " + deleteGenres);
            userGenreRepository.deleteAllByIdInQuery(deleteGenres, userId);

            // 추가할 장르 리스트 - 이전 리스트에는 없는데 현재 리스트에는 있는 장르
            List<Long> insertGenres = nowGenreList.stream()
                    .filter(g -> !preGenreList.contains(g))
                    .collect(Collectors.toList());
            System.out.println("insertGenres = " + insertGenres);
            insertGenres.stream()
                    .forEach(g -> {
                        UserGenre userGenre = UserGenre.create(
                                user,
                                genreRepository.findById(g)
                                        .orElseThrow(() -> new NotFoundException(ErrorCode.GENRE_NOT_FOUND)),
                                false
                        );
                        userGenreRepository.save(userGenre);
                    });
        } else { // 이전 선택한 2순위 장르가 없는 경우
            nowGenreList.stream()
                    .forEach(g -> {
                        UserGenre userGenre = UserGenre.create(
                                user,
                                genreRepository.findById(g)
                                        .orElseThrow(() -> new NotFoundException(ErrorCode.GENRE_NOT_FOUND)),
                                false
                        );
                        userGenreRepository.save(userGenre);
                    });
        }

        return userId;
    }
}
