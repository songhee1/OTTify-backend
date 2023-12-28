package tavebalak.OTTify.oauth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tavebalak.OTTify.error.exception.NotFoundException;
import tavebalak.OTTify.genre.entity.UserGenre;
import tavebalak.OTTify.genre.repository.GenreRepository;
import tavebalak.OTTify.genre.repository.UserGenreRepository;
import tavebalak.OTTify.oauth.dto.SignUpInfoDto;
import tavebalak.OTTify.program.repository.OttRepository;
import tavebalak.OTTify.user.entity.User;
import tavebalak.OTTify.user.entity.UserSubscribingOTT;
import tavebalak.OTTify.user.repository.UserRepository;
import tavebalak.OTTify.user.repository.UserSubscribingOTTRepository;

import java.util.List;

import static tavebalak.OTTify.error.ErrorCode.ENTITY_NOT_FOUND;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OauthService {
    private final UserRepository userRepository;
    private final OttRepository ottRepository;
    private final UserSubscribingOTTRepository userSubscribingOTTRepository;
    private final UserGenreRepository userGenreRepository;
    private final GenreRepository genreRepository;

    @Transactional
    public void saveCode(User user, String code) {
        user.changeCode(code);
    }

    @Transactional
    public String saveInformation(User user, SignUpInfoDto signUpInfoDto) {

        List<Long> ottIdList = signUpInfoDto.getOttList();
        Long firstGenreId = signUpInfoDto.getFirstGenre();
        List<Long> genreIdList = signUpInfoDto.getGenreList();

        ottIdList.stream().forEach(id -> {
            UserSubscribingOTT userSubscribingOTT = UserSubscribingOTT.create(
                    user,
                    ottRepository.findById(id)
                            .orElseThrow(() -> new NotFoundException(ENTITY_NOT_FOUND))
            );
            userSubscribingOTTRepository.save(userSubscribingOTT);
        });

        UserGenre firstUserGenre = UserGenre.create(
                user,
                genreRepository.findById(firstGenreId)
                        .orElseThrow(() -> new NotFoundException(ENTITY_NOT_FOUND)),
                true
        );
        userGenreRepository.save(firstUserGenre);


        if(!genreIdList.isEmpty()) {
            genreIdList.stream().forEach(id -> {
                UserGenre userGenre = UserGenre.create(
                        user,
                        genreRepository.findById(id)
                                .orElseThrow(() -> new NotFoundException(ENTITY_NOT_FOUND)),
                        false
                );
                userGenreRepository.save(userGenre);
            });
        }

        user.changeUserRole();
        return "추가정보 저장 완료";
    }

    public void logout() {
    }
}
