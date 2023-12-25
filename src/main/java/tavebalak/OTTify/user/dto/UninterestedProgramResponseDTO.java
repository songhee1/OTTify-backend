package tavebalak.OTTify.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UninterestedProgramResponseDTO {
    private Long programId;
    private String posterPath;

    @Builder
    public UninterestedProgramResponseDTO(Long programId, String posterPath) {
        this.programId = programId;
        this.posterPath = posterPath;
    }
}
