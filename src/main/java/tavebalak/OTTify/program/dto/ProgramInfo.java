package tavebalak.OTTify.program.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProgramInfo {
    private Long programId;
    private String title;

    public ProgramInfo(Long programId, String title) {
        this.programId = programId;
        this.title = title;
    }
}
