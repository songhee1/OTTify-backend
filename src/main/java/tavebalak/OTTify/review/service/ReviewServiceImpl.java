package tavebalak.OTTify.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tavebalak.OTTify.oauth.jwt.SecurityUtil;
import tavebalak.OTTify.review.dto.LatestReviewsDTO;
import tavebalak.OTTify.review.entity.Review;
import tavebalak.OTTify.review.repository.ReviewRepository;
import tavebalak.OTTify.user.entity.LikedReview;
import tavebalak.OTTify.user.entity.User;
import tavebalak.OTTify.user.repository.LikedReviewRepository;
import tavebalak.OTTify.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl  implements  ReviewService{
    private final ReviewRepository reviewRepository;
    private final LikedReviewRepository likedReviewRepository;
    private final UserRepository userRepository;

    public List<LatestReviewsDTO> getLatestReviews() {
        List<Review> reviewList = reviewRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        List<Review> top4ReviewList = new ArrayList<>(Arrays.asList(reviewList.get(0), reviewList.get(1), reviewList.get(2), reviewList.get(3)));
        return top4ReviewList.stream().map(
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
        return likedReviewRepository.findByReviewId(reviewId).size();
    }

    public void save(Review review){
        reviewRepository.save(review);
    }

    @Override
    public boolean likeReview(Long id) {
        AtomicBoolean flag = new AtomicBoolean(false);
        String userEmail = SecurityUtil.getCurrentEmail().get();
        User savedUser = userRepository.findByEmail(userEmail).orElseThrow(NoSuchElementException::new);
        Review review = reviewRepository.findById(id).orElseThrow(NoSuchElementException::new);
        likedReviewRepository.findByUserIdAndReviewId(savedUser.getId(), review.getId()).ifPresentOrElse(
                likedReviewRepository::delete,
                () -> {
                    likedReviewRepository.save(
                            LikedReview.builder()
                                    .user(savedUser)
                                    .review(reviewRepository.findById(id).orElseThrow(NoSuchElementException::new))
                                    .build()
                    );
                    flag.set(true);
                }
        );

        return flag.get();
    }
}
