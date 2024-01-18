package tavebalak.OTTify.program.dto.programDetails.Response;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProgramProviderListResponseDto {

    private List<ProgramProviderResponseDto> buy = new ArrayList<>();
    private List<ProgramProviderResponseDto> rent = new ArrayList<>();
    private List<ProgramProviderResponseDto> streaming = new ArrayList<>();

    private int buySize;
    private int rentSize;
    private int streamingSize;

    @Builder
    public ProgramProviderListResponseDto(List<ProgramProviderResponseDto> buy,
        List<ProgramProviderResponseDto> rent,
        List<ProgramProviderResponseDto> streaming,
        int buySize,
        int rentSize,
        int streamingSize
    ) {
        this.buy = buy;
        this.rent = rent;
        this.streaming = streaming;
        this.buySize = buySize;
        this.rentSize = rentSize;
        this.streamingSize = streamingSize;
    }


}
