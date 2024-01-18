package tavebalak.OTTify.program.dto.programDetails.Response;

import lombok.Getter;
import tavebalak.OTTify.program.dto.programDetails.openApiRequest.personDetails.OAProgramCreditsDto;

@Getter
public class ProgramResponseDto {

    private ProgramDetailResponse programDetailResponse;
    private OAProgramCreditsDto oaProgramCreditsDto;
    private ProgramProviderListResponseDto programProviderListResponseDto;
    private String programNormalReviewRating;

    public ProgramResponseDto(ProgramDetailResponse programDetailResponse,
        OAProgramCreditsDto oaProgramCreditsDto,
        ProgramProviderListResponseDto programProviderListResponseDto,
        double programNormalReviewRating) {
        this.programDetailResponse = programDetailResponse;
        this.oaProgramCreditsDto = oaProgramCreditsDto;
        this.programProviderListResponseDto = programProviderListResponseDto;
        this.programNormalReviewRating = String.format("%.1f", programNormalReviewRating);
    }
}
