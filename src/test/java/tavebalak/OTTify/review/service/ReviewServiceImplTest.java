package tavebalak.OTTify.review.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.review.dto.LatestReviewsDTO;
import tavebalak.OTTify.review.entity.Review;
import tavebalak.OTTify.review.repository.ReviewRepository;
import tavebalak.OTTify.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockBean(JpaMetamodelMappingContext.class)
@SpringBootTest
@AutoConfigureMockMvc
class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Nested
    @DisplayName("최신 리뷰 조회")
    class GetReivews{
        private Review review1;
        private Review review2;
        private Review review3;
        private Review review4;
        private Review review5;

        private Review save;


        @BeforeEach
        void setup() throws InstantiationException, IllegalAccessException {
            BDDMockito.given(reviewRepository.save(any(Review.class))).willReturn(Review.class.newInstance());

            review1 = Review.builder()
                    .content("test-content1")
                    .genre("test-genre")
                    .program(Program.builder().id(1L).title("test-title").build())
                    .rating(5.55)
                    .user(User.builder().id(1L).nickName("test-nickName").profilePhoto("test-url").averageRating(5.55).build())
                    .build();

            review2 = Review.builder()
                    .content("test-content2")
                    .genre("test-genre")
                    .program(Program.builder().id(1L).title("test-title").build())
                    .rating(5.55)
                    .user(User.builder().id(1L).nickName("test-nickName").profilePhoto("test-url").averageRating(5.55).build())
                    .build();
            review3 = Review.builder()
                    .content("test-content3")
                    .genre("test-genre")
                    .program(Program.builder().id(1L).title("test-title").build())
                    .rating(5.55)
                    .user(User.builder().id(1L).nickName("test-nickName").profilePhoto("test-url").averageRating(5.55).build())
                    .build();

            review4 = Review.builder()
                    .content("test-content4")
                    .genre("test-genre")
                    .program(Program.builder().id(1L).title("test-title").build())
                    .rating(5.55)
                    .user(User.builder().id(1L).nickName("test-nickName").profilePhoto("test-url").averageRating(5.55).build())
                    .build();

            review5 = Review.builder()
                    .content("test-content5")
                    .genre("test-genre")
                    .program(Program.builder().id(1L).title("test-title").build())
                    .rating(5.55)
                    .user(User.builder().id(1L).nickName("test-nickName").profilePhoto("test-url").averageRating(5.55).build())
                    .build();

            review1.setCreatedAt(LocalDateTime.now().minusDays(5));
            review2.setCreatedAt(LocalDateTime.now().minusDays(2));
            review3.setCreatedAt(LocalDateTime.now());
            review4.setCreatedAt(LocalDateTime.now().plusHours(1));
            review5.setCreatedAt(LocalDateTime.now().plusHours(2));

        }

        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase{
            @Test
            @DisplayName("최신 리뷰글 조회 성공")
            void getLatestReviewsSuccess(){
                when(reviewRepository.save(any(Review.class))).thenReturn(review1);
                when(reviewRepository.save(any(Review.class))).thenReturn(review2);
                when(reviewRepository.save(any(Review.class))).thenReturn(review3);
                when(reviewRepository.save(any(Review.class))).thenReturn(review4);
                when(reviewRepository.save(any(Review.class))).thenReturn(review5);

                System.out.println(save +","+ reviewRepository.findAll().size());
                List<Review> reviews = List.of(review5, review4, review3, review2);
                List<LatestReviewsDTO> collect = reviews.stream().map(review -> LatestReviewsDTO.builder()
                        .reviewId(review.getId())
                        .profilePhoto(review.getUser().getProfilePhoto())
                        .content(review.getContent())
                        .userAverageRating(review.getRating())
                        .build()).collect(Collectors.toList());

                List<Review> all = reviewRepository.findAll();
                System.out.println(all);

                ReviewService reviewService = new ReviewServiceImpl(reviewRepository);

                when(reviewService.getLatestReviews()).thenReturn(collect);

                reviewService = new ReviewServiceImpl(reviewRepository);
                List<LatestReviewsDTO> latestReviews = reviewService.getLatestReviews();

                assertThat(latestReviews.size()).isEqualTo(4);

            }
        }
    }

}