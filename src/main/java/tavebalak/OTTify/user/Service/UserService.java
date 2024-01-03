package tavebalak.OTTify.user.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tavebalak.OTTify.review.dto.MyReviewDto;
import tavebalak.OTTify.review.entity.Review;
import tavebalak.OTTify.review.entity.ReviewTag;
import tavebalak.OTTify.review.repository.LikedReviewRepository;
import tavebalak.OTTify.review.repository.ReviewRepository;
import tavebalak.OTTify.review.repository.ReviewReviewTagRepository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final ReviewRepository reviewRepository;
    private final ReviewReviewTagRepository reviewReviewTagRepository;
    private final LikedReviewRepository likedReviewRepository;

    public List<MyReviewDto> getMyReview(Long userId) {
        List<Review> reviewList = reviewRepository.findByUserId(userId);

        List<MyReviewDto> reviewDtoList = new ArrayList<>();
        reviewList.stream()
                        .forEach(r -> {
                            // createdDate format 변경
                            String createdDateString = r.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));

                            // 리뷰에 달린 reviewTags 가져오기
                            List<ReviewTag> reviewTags = reviewReviewTagRepository.findReviewTagNameByReviewId(r.getId());

                            reviewDtoList.add(
                                    MyReviewDto.builder()
                                            .userProfilePhoto(r.getUser().getProfilePhoto())
                                            .userNickName(r.getUser().getNickName())
                                            .createdDate(createdDateString)
                                            .programTitle(r.getProgram().getTitle())
                                            .rating(r.getRating())
                                            .reviewTags(reviewTags)
                                            .content(r.getContent())
                                            .likeCnt(r.getLikeCnt())
                                            .build()
                            );
                        });

        return reviewDtoList;
    }

    public List<MyReviewDto> getLikedReview(Long userId) {
        List<Review> reviewList = likedReviewRepository.findLikedReviewByUserId(userId);

        List<MyReviewDto> reviewDtoList = new ArrayList<>();
        reviewList.stream()
                .forEach(r -> {
                    // createdDate format 변경
                    String createdDateString = r.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));

                    // 리뷰에 달린 reviewTags 가져오기
                    List<ReviewTag> reviewTags = reviewReviewTagRepository.findReviewTagNameByReviewId(r.getId());

                    reviewDtoList.add(
                            MyReviewDto.builder()
                                    .userProfilePhoto(r.getUser().getProfilePhoto())
                                    .userNickName(r.getUser().getNickName())
                                    .createdDate(createdDateString)
                                    .programTitle(r.getProgram().getTitle())
                                    .rating(r.getRating())
                                    .reviewTags(reviewTags)
                                    .content(r.getContent())
                                    .likeCnt(r.getLikeCnt())
                                    .build()
                    );
                });

        return reviewDtoList;
    }
}
