package tavebalak.OTTify.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.review.dto.response.LatestReviewsDTO;
import tavebalak.OTTify.review.entity.Review;
import tavebalak.OTTify.review.repository.ReviewRepository;
import tavebalak.OTTify.user.entity.User;
import tavebalak.OTTify.user.repository.LikedReviewRepository;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private LikedReviewRepository likedReviewRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;
    User.TestUserBuilder testUserBuilder = User.testUserBuilder();


    @DisplayName("최신 리뷰 목록 조회 성공")
    @Test
    void getReviesList() throws Exception {
        //given 입력값과 리턴값을 작성
        User user = testUserBuilder.create(1L, "test-nickName", "test-url", 5.55);

        Review review1 = Review.builder()
            .content("test-content1")
            .genre("test-genre")
            .program(Program.testBuilder().id(1L).title("test-title").build())
            .rating(5.55)
            .user(user)
            .build();

        Review review2 = Review.builder()
            .content("test-content2")
            .genre("test-genre")
            .program(Program.testBuilder().id(1L).title("test-title").build())
            .rating(5.55)
            .user(user)
            .build();
        Review review3 = Review.builder()
            .content("test-content3")
            .genre("test-genre")
            .program(Program.testBuilder().id(1L).title("test-title").build())
            .rating(5.55)
            .user(user)
            .build();
        Review review4 = Review.builder()
            .content("test-content4")
            .genre("test-genre")
            .program(Program.testBuilder().id(1L).title("test-title").build())
            .rating(5.55)
            .user(user)
            .build();
        Review review5 = Review.builder()
            .content("test-content5")
            .genre("test-genre")
            .program(Program.testBuilder().id(1L).title("test-title").build())
            .rating(5.55)
            .user(user)
            .build();

        when(reviewRepository.save(any())).thenReturn(review1)
            .thenReturn(review2)
            .thenReturn(review3)
            .thenReturn(review4)
            .thenReturn(review5);

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
        List<LatestReviewsDTO> latestReviews = reviewService.getLatestReviewsTest();
        List<LatestReviewsDTO> collect = list.stream().map(review -> LatestReviewsDTO.builder()
            .reviewId(review.getId())
            .programTitle(review.getProgram().getTitle())
            .content(review.getContent())
            .reviewRating(review.getRating())
            .profilePhoto(review.getUser().getProfilePhoto())
            .nickName(review.getUser().getNickName())
            .build()).collect(Collectors.toList());

        LatestReviewsDTO review1DTO = LatestReviewsDTO.builder()
            .reviewId(review1.getId())
            .programTitle(review1.getProgram().getTitle())
            .content(review1.getContent())
            .reviewRating(review1.getRating())
            .profilePhoto(review1.getUser().getProfilePhoto())
            .nickName(review1.getUser().getNickName()).build();

        //then
        assertThat(latestReviews).isNotNull();
        assertThat(latestReviews.size()).isEqualTo(4);
        assertThat(latestReviews)
            .contains(collect.get(3), collect.get(2), collect.get(1), collect.get(0))
            .doesNotContain(review1DTO);
        assertThat(latestReviews).startsWith(collect.get(0)).endsWith(collect.get(3));
    }

    @DisplayName("최신 리뷰 목록 조회 실패 - 최신순이 아닌 경우")
    @Test
    void getNotLatestReviesList() throws Exception {
        //given 입력값과 리턴값을 작성
        Review review1 = Review.builder()
            .content("test-content1")
            .genre("test-genre")
            .program(Program.testBuilder().id(1L).title("test-title").build())
            .rating(5.55)
            .user(testUserBuilder.create(1L, "test-nickName", "test-url", 5.55))
            .build();

        Review review2 = Review.builder()
            .content("test-content2")
            .genre("test-genre")
            .program(Program.testBuilder().id(1L).title("test-title").build())
            .rating(5.55)
            .user(testUserBuilder.create(1L, "test-nickName", "test-url", 5.55))
            .build();
        Review review3 = Review.builder()
            .content("test-content3")
            .genre("test-genre")
            .program(Program.testBuilder().id(1L).title("test-title").build())
            .rating(5.55)
            .user(testUserBuilder.create(1L, "test-nickName", "test-url", 5.55))
            .build();
        Review review4 = Review.builder()
            .content("test-content4")
            .genre("test-genre")
            .program(Program.testBuilder().id(1L).title("test-title").build())
            .rating(5.55)
            .user(testUserBuilder.create(1L, "test-nickName", "test-url", 5.55))
            .build();
        Review review5 = Review.builder()
            .content("test-content5")
            .genre("test-genre")
            .program(Program.testBuilder().id(1L).title("test-title").build())
            .rating(5.55)
            .user(testUserBuilder.create(1L, "test-nickName", "test-url", 5.55))
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
        List<Review> allList = List.of(review4, review3, review2, review1);

        when(reviewRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))).thenReturn(list);

        //when
        List<LatestReviewsDTO> latestReviews = reviewService.getLatestReviews();

        List<LatestReviewsDTO> allCollect = allList.stream()
            .map(review -> LatestReviewsDTO.builder()
                .reviewId(review.getId())
                .programTitle(review.getProgram().getTitle())
                .content(review.getContent())
                .reviewRating(review.getRating())
                .profilePhoto(review.getUser().getProfilePhoto())
                .nickName(review.getUser().getNickName())
                .build()).collect(Collectors.toList());

        //then
        assertThat(latestReviews).doesNotContain(allCollect.get(3));
        assertThatThrownBy(() -> {
            String content = latestReviews.get(3).getContent();
            if (!content.equals("test-content1")) {
                throw new NoSuchElementException();
            }
        }).isInstanceOf(NoSuchElementException.class);
        assertThat(latestReviews).doesNotContainSequence(allCollect);

    }


}