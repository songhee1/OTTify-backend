package tavebalak.OTTify.program.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ServiceListsDTO {
    private Long programId;
    private String title;
    private String posterPath;
    private double averageRating;
    @Builder
    public ServiceListsDTO(Long programId, String title, String posterPath, double averageRating) {
        this.programId = programId;
        this.title = title;
        this.posterPath = posterPath;
        this.averageRating = averageRating;
    }
}
