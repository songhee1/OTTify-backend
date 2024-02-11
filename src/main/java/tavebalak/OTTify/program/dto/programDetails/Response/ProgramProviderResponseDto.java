package tavebalak.OTTify.program.dto.programDetails.Response;

import lombok.Getter;

@Getter
public class ProgramProviderResponseDto {

    private String logo_path;
    private String provider_name;

    public ProgramProviderResponseDto(String logo_path, String provider_name) {
        this.logo_path = logo_path;
        this.provider_name = provider_name;
    }

}
