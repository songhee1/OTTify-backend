package tavebalak.OTTify.review.service;


import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tavebalak.OTTify.error.ErrorCode;
import tavebalak.OTTify.error.exception.NotFoundException;
import tavebalak.OTTify.genre.entity.Genre;
import tavebalak.OTTify.genre.repository.UserGenreRepository;
import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.program.repository.ProgramRepository;
import tavebalak.OTTify.review.dto.reviewresponse.FourReviewResponseWithCounts;
import tavebalak.OTTify.review.dto.reviewresponse.ReviewListWithSliceInfoDto;
import tavebalak.OTTify.review.dto.reviewresponse.ReviewProgramResponseDto;
import tavebalak.OTTify.review.entity.Review;
import tavebalak.OTTify.review.repository.ReviewRepository;
import tavebalak.OTTify.review.repository.ReviewReviewTagRepository;
import tavebalak.OTTify.user.entity.User;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewShowProgramDetailServiceImpl implements ReviewShowProgramDetailService {

    private final ReviewRepository reviewRepository;
    private final ProgramRepository programRepository;
    private final UserGenreRepository userGenreRepository;
    private final ReviewReviewTagRepository reviewReviewTagRepository;


    //내가 작성한 리뷰를 조회합니다.
    @Override
    public ReviewProgramResponseDto showMyReview(User user, Long programId) {
        Program program = programRepository.findById(programId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PROGRAM_NOT_FOUND));

        return reviewRepository.findByProgramAndUser(program, user).map(this::makeReviewDto)
            .orElse(null);

    }

    //상세페이지에서 4개의 장르 상관없는 좋아요 순 리뷰를 보여줍니다

    @Override
    public FourReviewResponseWithCounts show4Review(Long programId) {

        Program program = programRepository.findById(programId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PROGRAM_NOT_FOUND));

        List<ReviewProgramResponseDto> reviewProgramResponseDtoList = reviewRepository.findTop4ByProgramOrderByLikeCountsDesc(
            program).stream().map(this::makeReviewDto).collect(
            Collectors.toList());

        int leftReviewCounts = program.getReviewCount() - 4 > 0 ? program.getReviewCount() - 4 : 0;

        return new FourReviewResponseWithCounts(
            reviewProgramResponseDtoList, leftReviewCounts);
    }

    //모든 장르 상관 없는 리뷰 리스트를 Slice로  보여줍니다.

    @Override
    public ReviewListWithSliceInfoDto showReviewList(Long programId, Pageable pageable) {
        Program program = programRepository.findById(programId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PROGRAM_NOT_FOUND));

        Slice<ReviewProgramResponseDto> reviewProgramResponseDtoSlice = reviewRepository.findByProgram(
            program, pageable).map(r -> makeReviewDto(r));

        return new ReviewListWithSliceInfoDto(reviewProgramResponseDtoSlice.getContent(),
            reviewProgramResponseDtoSlice.hasNext());
    }

    //상세페이지에서 4개의 장르 상관있는 User 에 특화된 좋아요 순 리뷰를 보여줍니다.

    @Override

    public FourReviewResponseWithCounts show4UserSpecificReviewList(User user, Long programId) {

        Program program = programRepository.findById(programId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PROGRAM_NOT_FOUND));

        Genre usersFirstGenre = userGenreRepository.findByUserAndIsFirst(user, true)
            .orElseThrow(() -> new NotFoundException(ErrorCode.USER_FIRST_GENRE_NOT_FOUND))
            .getGenre();

        List<ReviewProgramResponseDto> reviewProgramResponseDtoList = reviewRepository.findUserSpecific4ByProgramAndGenreOrderByLikeNumberDesc(
                program, usersFirstGenre.getName(), PageRequest.of(0, 4)).stream()
            .map(r -> makeReviewDto(r)).collect(Collectors.toList());

        int userSpecificGenreCount = reviewRepository.countByGenreName(usersFirstGenre.getName(),
            program);

        int leftCount = userSpecificGenreCount - 4 > 0 ? userSpecificGenreCount - 4 : 0;

        return new FourReviewResponseWithCounts(
            reviewProgramResponseDtoList, leftCount);

    }

    // 유저의 first genre 를 기반으로 리뷰리스트를 slice로 보여줍니다

    @Override
    public ReviewListWithSliceInfoDto showUserSpecificReviewList(User user, Long programId,
        Pageable pageable) {

        Program program = programRepository.findById(programId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PROGRAM_NOT_FOUND));

        Genre usersFirstGenre = userGenreRepository.findByUserAndIsFirst(user, true)
            .orElseThrow(() -> new NotFoundException(ErrorCode.USER_FIRST_GENRE_NOT_FOUND))
            .getGenre();

        Slice<ReviewProgramResponseDto> reviewProgramResponseDtoSlice = reviewRepository.findUserSpecificByProgramAndGenre(
            program,
            usersFirstGenre.getName(), pageable).map(r -> makeReviewDto(r));

        return new ReviewListWithSliceInfoDto(reviewProgramResponseDtoSlice.getContent(),
            reviewProgramResponseDtoSlice.hasNext());

    }

    //리뷰를 이용해 리뷰 DTO를 만듭니다

    private ReviewProgramResponseDto makeReviewDto(Review review) {

        List<String> reviewTagNames = reviewReviewTagRepository.findByReviewWithReviewTagFetch(
                review.getId())
            .stream().map(reviewReviewTag -> reviewReviewTag.getReviewTag().getName())
            .collect(Collectors.toList());

        ReviewProgramResponseDto reviewProgramResponseDto = ReviewProgramResponseDto.builder()
            .reviewTagNames(reviewTagNames)
            .createdDateTime(review.getCreatedAt())
            .like(review.getLikeCounts())
            .ratings(review.getRating())
            .contents(review.getContent())
            .userNickName(review.getUser().getNickName())
            .userPosterPath(review.getUser().getProfilePhoto())
            .reviewId(review.getId())
            .build();

        return reviewProgramResponseDto;
    }


}
