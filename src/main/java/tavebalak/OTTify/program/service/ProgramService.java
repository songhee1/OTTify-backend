package tavebalak.OTTify.program.service;

import tavebalak.OTTify.program.dto.RecommendProgramsDTO;

public interface ProgramService {
    public RecommendProgramsDTO getRecommendProgram(int count);
}
