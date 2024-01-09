package tavebalak.OTTify.community.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CommunitySubjectsDTO {
    private int subjectAmount;
    private List<CommunitySubjectsListDTO> list;

    @Builder
    public CommunitySubjectsDTO(int subjectAmount, List<CommunitySubjectsListDTO> list){
        this.subjectAmount = subjectAmount;
        this.list = list;
    }
}
