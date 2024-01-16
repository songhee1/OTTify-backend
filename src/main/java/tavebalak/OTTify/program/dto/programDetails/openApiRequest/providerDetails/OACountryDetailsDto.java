package tavebalak.OTTify.program.dto.programDetails.openApiRequest.providerDetails;


import java.util.List;
import lombok.Getter;

@Getter
public class OACountryDetailsDto {

    //private String link;
    private List<OAProviderDetailsDto> buy;
    private List<OAProviderDetailsDto> rent;
    private List<OAProviderDetailsDto> flatrate;

    private int buySize;
    private int rentSize;
    private int flatrateSize;

    public void initSize(int buySize, int rentSize, int flatrateSize) {
        this.buySize = buySize;
        this.rentSize = rentSize;
        this.flatrateSize = flatrateSize;
    }
}
