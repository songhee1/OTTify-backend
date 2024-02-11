package tavebalak.OTTify.program.service;

import tavebalak.OTTify.program.dto.programDetails.Response.ProgramResponseDto;
import tavebalak.OTTify.program.dto.response.UserSpecificRatingResponseDto;
import tavebalak.OTTify.user.entity.User;

public interface ProgramDetailsShowService {

    ProgramResponseDto showDetails(Long programId);

    UserSpecificRatingResponseDto showUserSpecificRating(User user, Long programId);
}
