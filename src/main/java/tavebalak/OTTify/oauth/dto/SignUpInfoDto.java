package tavebalak.OTTify.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpInfoDto {
    private List<Long> ottList;
    @NotNull
    private Long firstGenre;
    private List<Long> genreList;
}
