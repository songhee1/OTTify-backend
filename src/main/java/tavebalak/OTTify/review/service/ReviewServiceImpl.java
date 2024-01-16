package tavebalak.OTTify.review.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tavebalak.OTTify.error.ErrorCode;
import tavebalak.OTTify.error.exception.NotFoundException;
import tavebalak.OTTify.oauth.jwt.SecurityUtil;
import tavebalak.OTTify.review.dto.response.LatestReviewsDTO;
import tavebalak.OTTify.review.entity.Review;
import tavebalak.OTTify.review.repository.ReviewRepository;
import tavebalak.OTTify.user.entity.LikedReview;
import tavebalak.OTTify.user.entity.User;
import tavebalak.OTTify.user.repository.LikedReviewRepository;
import tavebalak.OTTify.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final LikedReviewRepository likedReviewRepository;
    private final UserRepository userRepository;
    private final int TOP8_SIZE = 8;

    public List<LatestReviewsDTO> getLatestReviews() {
        List<Review> reviewList = reviewRepository.findAll(
            Sort.by(Sort.Direction.DESC, "createdAt"));
        List<Review> top8ReviewList = new ArrayList<>();
        if (reviewList.size() < TOP8_SIZE) {
            top8ReviewList.addAll(reviewList);
        } else {
            top8ReviewList.addAll(reviewList.subList(0, TOP8_SIZE));
        }
        return top8ReviewList.stream().map(
            listOne -> LatestReviewsDTO.builder()
                .reviewId(listOne.getId())
                .nickName(listOne.getUser().getNickName())
                .content(listOne.getContent())
                .programTitle(listOne.getProgram().getTitle())
                .userRating(listOne.getRating())
                .profilePhoto(listOne.getUser().getProfilePhoto())
                .likeCount(getLikeSum(listOne.getId()))
                .build()
        ).collect(Collectors.toList());

    }

    private Integer getLikeSum(Long reviewId) {
        Optional<List<LikedReview>> likedReviews = likedReviewRepository.findByReviewId(reviewId);
        return likedReviews
            .map(List::size)
            .orElse(0);
    }

    public List<LatestReviewsDTO> getLatestReviewsTest() {
        List<Review> reviewList = reviewRepository.findAll(
            Sort.by(Sort.Direction.DESC, "createdAt"));
        List<Review> top8ReviewList = new ArrayList<>();
        if (reviewList.size() < TOP8_SIZE) {
            top8ReviewList.addAll(reviewList);
        } else {
            top8ReviewList.addAll(reviewList.subList(0, TOP8_SIZE));
        }
        return top8ReviewList.stream().map(
            listOne -> LatestReviewsDTO.builder()
                .reviewId(listOne.getId())
                .nickName(listOne.getUser().getNickName())
                .content(listOne.getContent())
                .programTitle(listOne.getProgram().getTitle())
                .userRating(listOne.getRating())
                .profilePhoto(listOne.getUser().getProfilePhoto())
                .likeCount(0)
                .build()
        ).collect(Collectors.toList());

    }


    public void save(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public boolean likeReview(Long id) {
        AtomicBoolean flag = new AtomicBoolean(false);
        String userEmail = SecurityUtil.getCurrentEmail().get();
        User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        Review review = reviewRepository.findById(id).orElseThrow(() -> new NotFoundException(
            ErrorCode.REVIEW_NOT_FOUND));
        likedReviewRepository.findByUserIdAndReviewId(savedUser.getId(), review.getId())
            .ifPresentOrElse(
                likedReviewRepository::delete,
                () -> {
                    likedReviewRepository.save(
                        LikedReview.builder()
                            .user(savedUser)
                            .review(reviewRepository.findById(id)
                                .orElseThrow(NoSuchElementException::new))
                            .build()
                    );
                    flag.set(true);
                }
            );

        if (flag.get()) {
            review.addLikeNumber();
        } else {
            review.cancelLikeNumber();
        }

        return flag.get();
    }
}
