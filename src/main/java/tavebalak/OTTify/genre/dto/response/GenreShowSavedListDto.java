package tavebalak.OTTify.genre.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class GenreShowSavedListDto {

    List<GenreShowSavedDto> genreShowSavedDtos = new ArrayList<>();

    public GenreShowSavedListDto(List<GenreShowSavedDto> genreShowSavedDtos) {
        this.genreShowSavedDtos = genreShowSavedDtos;
    }
}
