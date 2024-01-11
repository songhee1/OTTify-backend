package tavebalak.OTTify.genre.service;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import tavebalak.OTTify.genre.dto.OpenApiGenreListResponseDto;
import tavebalak.OTTify.genre.entity.Genre;
import tavebalak.OTTify.genre.repository.GenreRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GenreService {

    private final GenreRepository genreRepository;
    private final WebClient webClient;


    @Transactional
    public void saveAllGenre() {
        OpenApiGenreListResponseDto movieGenreDto = getGenreListFrom("movie");
        saveGenre(movieGenreDto);
        OpenApiGenreListResponseDto tvGenreDto = getGenreListFrom("tv");
        saveGenre(tvGenreDto);
    }

    private OpenApiGenreListResponseDto getGenreListFrom(String type) {
        return webClient.get()
            .uri("/genre/" + type + "/list?language=ko")
            .retrieve()
            .bodyToMono(OpenApiGenreListResponseDto.class)
            .block();
    }

    @Transactional
    public void saveGenre(OpenApiGenreListResponseDto openApiGenreListResponseDto) {

        Map<String, String> genreMapping = new HashMap<>();

        genreMapping.put("Action & Adventure", "액션 및 어드벤쳐");
        genreMapping.put("Kids", "유아");
        genreMapping.put("News", "뉴스");
        genreMapping.put("Reality", "리얼리티");
        genreMapping.put("Sci-Fi & Fantasy", "공상 과학 및 판타지");
        genreMapping.put("Soap", "소프");
        genreMapping.put("Talk", "토크");
        genreMapping.put("War & Politics", "전쟁과 정치");

        openApiGenreListResponseDto.getGenres().stream().map(gd -> {

            Genre.GenreBuilder genreBuilder = Genre.builder()
                .tmDbGenreId(gd.getId());

            String genreName = gd.getName();
            if (!genreMapping.containsKey(genreName)) {
                return genreBuilder.name(genreName).build();
            } else {
                return genreBuilder.name(genreMapping.get(genreName)).build();
            }
        }).forEach(g -> {
            if (!genreRepository.existsByTmDbGenreId(g.getTmDbGenreId())) {
                genreRepository.save(g);
            }
        });
    }

}
