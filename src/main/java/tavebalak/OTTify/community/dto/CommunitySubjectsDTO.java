package tavebalak.OTTify.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CommunitySubjectsDTO {
    private long subjectAmount;
    private List<CommunitySubjectsListDTO> list;

    @Builder
    public CommunitySubjectsDTO(long subjectAmount, List<CommunitySubjectsListDTO> list){
        this.subjectAmount = subjectAmount;
        this.list = list;
    }
}