package tavebalak.OTTify.genre.dto.response;

import lombok.Getter;
import tavebalak.OTTify.genre.entity.Genre;

@Getter
public class GenreShowSavedDto {

    private Long id;
    private String name;

    public GenreShowSavedDto(Genre genre) {
        this.id = genre.getId();
        this.name = genre.getName();
    }
}
