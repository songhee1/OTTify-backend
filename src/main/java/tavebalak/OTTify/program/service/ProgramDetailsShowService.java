package tavebalak.OTTify.program.service;

import tavebalak.OTTify.program.dto.programDetails.Response.ProgramResponseDto;

public interface ProgramDetailsShowService {
     ProgramResponseDto showDetails(Long programId);
}
