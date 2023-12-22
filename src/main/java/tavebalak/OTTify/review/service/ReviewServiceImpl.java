package tavebalak.OTTify.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tavebalak.OTTify.review.dto.LatestReviewsDTO;
import tavebalak.OTTify.review.entity.Review;
import tavebalak.OTTify.review.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl  implements  ReviewService{
    private final ReviewRepository reviewRepository;

    public List<LatestReviewsDTO> getLatestReviews() {
        List<Review> reviewList = reviewRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        System.out.println(reviewList.size());
        List<Review> top4ReviewList = new ArrayList<>(Arrays.asList(reviewList.get(0), reviewList.get(1), reviewList.get(2), reviewList.get(3)));
        for (Review review : top4ReviewList) {
            System.out.println(review);
        }
        return top4ReviewList.stream().map(
                listOne -> LatestReviewsDTO.builder()
                        .reviewId(listOne.getId())
                        .nickName(listOne.getUser().getNickName())
                        .content(listOne.getContent())
                        .programTitle(listOne.getProgram().getTitle())
                        .userRating(listOne.getRating())
                        .profilePhoto(listOne.getUser().getProfilePhoto())
                        .build()
        ).collect(Collectors.toList());

    }

    public void save(Review review){
        reviewRepository.save(review);
    }
}
