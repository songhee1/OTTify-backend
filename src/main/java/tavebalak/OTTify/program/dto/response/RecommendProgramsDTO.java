package tavebalak.OTTify.program.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RecommendProgramsDTO {
    private int recommentAmount;
    private List<ServiceListsDTO> serviceListsDTOList;

    @Builder
    public RecommendProgramsDTO(int recommentAmount, List<ServiceListsDTO> serviceListsDTOList) {
        this.recommentAmount = recommentAmount;
        this.serviceListsDTOList = serviceListsDTOList;
    }
}
