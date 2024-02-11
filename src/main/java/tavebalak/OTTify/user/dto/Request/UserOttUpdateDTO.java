package tavebalak.OTTify.user.dto.Request;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserOttUpdateDTO {

    @NotNull
    private List<Long> ottList;
}
