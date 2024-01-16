package tavebalak.OTTify.program.dto.programDetails.Response;

import lombok.Getter;
import tavebalak.OTTify.program.dto.programDetails.openApiRequest.personDetails.OAProgramCreditsDto;
import tavebalak.OTTify.program.dto.programDetails.openApiRequest.providerDetails.OACountryDetailsDto;

@Getter
public class ProgramResponseDto {

    private ProgramDetailResponse programDetailResponse;
    private OAProgramCreditsDto oaProgramCreditsDto;
    private OACountryDetailsDto OACountryDetailsDto;
    private String programNormalReviewRating;

    public ProgramResponseDto(ProgramDetailResponse programDetailResponse,
        OAProgramCreditsDto oaProgramCreditsDto, OACountryDetailsDto OACountryDetailsDto,
        double programNormalReviewRating) {
        this.programDetailResponse = programDetailResponse;
        this.oaProgramCreditsDto = oaProgramCreditsDto;
        this.OACountryDetailsDto = OACountryDetailsDto;
        this.programNormalReviewRating = String.format("%.1f", programNormalReviewRating);
    }
}
