package tavebalak.OTTify.genre.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tavebalak.OTTify.genre.dto.response.GenreShowSavedDto;
import tavebalak.OTTify.genre.dto.response.GenreShowSavedListDto;
import tavebalak.OTTify.genre.repository.GenreRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GenreShowSavedService {

    private final GenreRepository genreRepository;

    public GenreShowSavedListDto showGenreList() {
        List<GenreShowSavedDto> genreShowSavedDtoList = genreRepository.findAll().stream()
            .map(GenreShowSavedDto::new).collect(
                Collectors.toList());

        return new GenreShowSavedListDto(genreShowSavedDtoList);
    }

}
