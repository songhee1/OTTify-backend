package tavebalak.OTTify.user.dto.Response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UninterestedProgramDTO {
    @ApiModelProperty(value = "프로그램 id")
    private Long programId;

    @ApiModelProperty(value = "프로그램 포스터 url")
    private String posterPath;

    @Builder
    public UninterestedProgramDTO(Long programId, String posterPath) {
        this.programId = programId;
        this.posterPath = posterPath;
    }
}
