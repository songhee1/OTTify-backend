package tavebalak.OTTify.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class UserOttUpdateRequestDTO {

    @NotNull
    private Long id;
}
