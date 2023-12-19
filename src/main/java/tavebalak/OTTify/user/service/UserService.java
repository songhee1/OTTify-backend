package tavebalak.OTTify.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tavebalak.OTTify.exception.ErrorCode;
import tavebalak.OTTify.exception.NotFoundException;
import tavebalak.OTTify.genre.repository.UserGenreRepository;
import tavebalak.OTTify.review.dto.UserReviewRatingListDTO;
import tavebalak.OTTify.review.repository.ReviewRepository;
import tavebalak.OTTify.user.dto.LikedProgramDTO;
import tavebalak.OTTify.user.dto.UninterestedProgramDTO;
import tavebalak.OTTify.user.dto.UserProfileResponseDTO;
import tavebalak.OTTify.user.entity.User;
import tavebalak.OTTify.user.repository.LikedProgramRepository;
import tavebalak.OTTify.user.repository.UninterestedProgramRepository;
import tavebalak.OTTify.user.repository.UserRepository;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserGenreRepository userGenreRepository;
    private final ReviewRepository reviewRepository;
    private final LikedProgramRepository likedProgramRepository;
    private final UninterestedProgramRepository uninterestedProgramRepository;

    public UserProfileResponseDTO getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        // 1순위 & 2순위 장르 가져오기
        String firstGenre = userGenreRepository.findFirstGenre(userId);
        List<String> secondGenre = userGenreRepository.findSecondGenre(userId);

        // 별점 리스트 가져오기
        HashMap<Double, Integer> ratingList = new HashMap<Double, Integer>();
        ArrayList<Double> ratingSet = new ArrayList<>(Arrays.asList(0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0));

        List<Double> reviewRatingList = reviewRepository.findReviewRatingByUserId(userId);
        for (Double r : ratingSet) {
            if (Collections.frequency(reviewRatingList, r) == 0) {
                ratingList.put(r, 0);
            }
            else {
                ratingList.put(r, Collections.frequency(reviewRatingList, r));
            }
        }

        UserReviewRatingListDTO userReviewRatingListDTO = UserReviewRatingListDTO.builder()
                .totalCnt(reviewRatingList.size())
                .pointFiveCnt(ratingList.get(0.5))
                .oneCnt(ratingList.get(1.0))
                .oneDotFiveCnt(ratingList.get(1.5))
                .twoCnt(ratingList.get(2.0))
                .twoDotFiveCnt(ratingList.get(2.5))
                .threeCnt(ratingList.get(3.0))
                .threeDotFiveCnt(ratingList.get(3.5))
                .fourCnt(ratingList.get(4.0))
                .fourDotFiveCnt(ratingList.get(4.5))
                .fiveCnt(ratingList.get(5.0))
                .build();

        // 보고싶은 프로그램 가져오기
        List<LikedProgramDTO> likedProgramListDTOList = likedProgramRepository.findLikedProgram(userId);

        // 관심없는 프로그램 가져오기
        List<UninterestedProgramDTO> uninterestedProgramDTOList = uninterestedProgramRepository.findUninterestedProgram(userId);

        return UserProfileResponseDTO.builder()
                .profilePhoto(user.getProfilePhoto())
                .nickName(user.getNickName())
                .userType(user.getUserType())
                .email(user.getEmail())
                .averageRating(user.getAverageRating())
                .firstGenre(firstGenre)
                .secondGenre(secondGenre)
                .ratingList(userReviewRatingListDTO)
                .likedProgram(likedProgramListDTOList)
                .uninterestedProgram(uninterestedProgramDTOList)
                .build();
    }
}
