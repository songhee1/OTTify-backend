package tavebalak.OTTify.review.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.review.dto.LatestReviewsDTO;
import tavebalak.OTTify.review.entity.Review;
import tavebalak.OTTify.review.repository.ReviewRepository;
import tavebalak.OTTify.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;


    @DisplayName("최신 리뷰 목록 조회")
    @Test
    void getReviesList() throws  Exception{
        //given 입력값과 리턴값을 작성
        Review review1 = Review.builder()
                .content("test-content1")
                .genre("test-genre")
                .program(Program.builder().id(1L).title("test-title").build())
                .rating(5.55)
                .user(User.builder().id(1L).nickName("test-nickName").profilePhoto("test-url").averageRating(5.55).build())
                .build();

        Review review2 = Review.builder()
                .content("test-content2")
                .genre("test-genre")
                .program(Program.builder().id(1L).title("test-title").build())
                .rating(5.55)
                .user(User.builder().id(1L).nickName("test-nickName").profilePhoto("test-url").averageRating(5.55).build())
                .build();
        Review review3 = Review.builder()
                .content("test-content3")
                .genre("test-genre")
                .program(Program.builder().id(1L).title("test-title").build())
                .rating(5.55)
                .user(User.builder().id(1L).nickName("test-nickName").profilePhoto("test-url").averageRating(5.55).build())
                .build();
        Review review4 = Review.builder()
                .content("test-content4")
                .genre("test-genre")
                .program(Program.builder().id(1L).title("test-title").build())
                .rating(5.55)
                .user(User.builder().id(1L).nickName("test-nickName").profilePhoto("test-url").averageRating(5.55).build())
                .build();
        Review review5 = Review.builder()
                .content("test-content5")
                .genre("test-genre")
                .program(Program.builder().id(1L).title("test-title").build())
                .rating(5.55)
                .user(User.builder().id(1L).nickName("test-nickName").profilePhoto("test-url").averageRating(5.55).build())
                .build();

        when(reviewRepository.save(any())).thenReturn(review1);
        when(reviewRepository.save(any())).thenReturn(review2);
        when(reviewRepository.save(any())).thenReturn(review3);
        when(reviewRepository.save(any())).thenReturn(review4);
        when(reviewRepository.save(any())).thenReturn(review5);

        review1.setCreatedAt(LocalDateTime.now().minusDays(5));
        review2.setCreatedAt(LocalDateTime.now().minusDays(2));
        review3.setCreatedAt(LocalDateTime.now());
        review4.setCreatedAt(LocalDateTime.now().plusHours(1));
        review5.setCreatedAt(LocalDateTime.now().plusHours(2));

        reviewService.save(review1);
        reviewService.save(review2);
        reviewService.save(review3);
        reviewService.save(review4);
        reviewService.save(review5);

        List<Review> list = List.of(review5, review4, review3, review2);

        when(reviewRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))).thenReturn(list);

        //when
        List<LatestReviewsDTO> latestReviews = reviewService.getLatestReviews();
        List<LatestReviewsDTO> collect = list.stream().map(review -> LatestReviewsDTO.builder()
                .reviewId(review.getId())
                .programTitle(review.getProgram().getTitle())
                .content(review.getContent())
                .userRating(review.getRating())
                .profilePhoto(review.getUser().getProfilePhoto())
                .nickName(review.getUser().getNickName())
                .build()).collect(Collectors.toList());

        //then
        assertThat(latestReviews).isNotNull();
        assertThat(latestReviews.size()).isEqualTo(4);
        assertThat(latestReviews).contains(collect.get(3), collect.get(2), collect.get(1), collect.get(0));

    }


}