package tavebalak.OTTify.genre.service;
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
    public void saveAllGenre(){
        OpenApiGenreListResponseDto movieGenreDto=getGenreListFrom("movie");
        saveGenre(movieGenreDto);
        OpenApiGenreListResponseDto tvGenreDto=getGenreListFrom("tv");
        saveGenre(tvGenreDto);
    }

    private OpenApiGenreListResponseDto getGenreListFrom(String type){
        return webClient.get()
                .uri("/genre/"+type+"/list?language=ko")
                .retrieve()
                .bodyToMono(OpenApiGenreListResponseDto.class)
                .block();
    }
    private void saveGenre(OpenApiGenreListResponseDto openApiGenreListResponseDto){
        openApiGenreListResponseDto.getGenres().stream().map(gd-> {
            String genreName=gd.getName();
            if(genreName.equals("Action & Adventure")){
                genreName="액션 및 어드벤쳐";
            }
            if(genreName.equals("Kids")){
                genreName="유아";
            }
            if(genreName.equals("News")){
                genreName="뉴스";
            }
            if(genreName.equals("Reality")){
                genreName="리얼리티";
            }
            if(genreName.equals("Sci-Fi & Fantasy")){
                genreName="공상 과학 및 판타지";
            }
            if(genreName.equals("Soap")){
                genreName="소프";
            }
            if(genreName.equals("Talk")){
                genreName="토크";
            }
            if(genreName.equals("War & Politics")){
                genreName = "전쟁과 정치";
            }
            return Genre.builder()
                    .tmDbGenreId(gd.getId())
                    .name(genreName)
                    .build();
        })
                .forEach(g->{
                    if(!genreRepository.existsByTmDbGenreId(g.getTmDbGenreId())){
                        genreRepository.save(g);
                    }
                });
    }
}
