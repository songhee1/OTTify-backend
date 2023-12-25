package tavebalak.OTTify.genre.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class SecondGenreUpdateRequestDTO {

    @NotNull
    private Long id;
}
