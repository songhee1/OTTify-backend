package tavebalak.OTTify.program.dto.searchTrending.Response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProgramInfo {
    private Long programId;
    private String title;

    public ProgramInfo(Long programId, String title) {
        this.programId = programId;
        this.title = title;
    }
}
