package tavebalak.OTTify.review.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.review.dto.LatestReviewsDTO;
import tavebalak.OTTify.review.entity.Review;
import tavebalak.OTTify.review.service.ReviewService;
import tavebalak.OTTify.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ReviewMainControllerTest {
    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewMainController reviewMainController;

    private MockMvc mockMvc;

    @BeforeEach
    public void init(){ // mockMvc 초기화, 각메서드가 실행되기전에 초기화 되게 함
        mockMvc = MockMvcBuilders.standaloneSetup(reviewMainController).build();
        // standaloneMockMvcBuilder() 호출하고 스프링 테스트 설정 커스텀으로 mockMvc 객체 생성

    }

    @Test
    @DisplayName("최신 리뷰 목록 조회 성공")
    public void getLatestReviews1() throws Exception{
        //given
        //request 입력값과 mock객체 리턴값
        //given 입력값과 리턴값을 작성
        User.TestUserBuilder testUserBuilder = User.testUserBuilder();

        Review review1 = Review.builder()
                .content("test-content1")
                .genre("test-genre")
                .program(Program.builder().id(1L).title("test-title").build())
                .rating(5.55)
                .user(testUserBuilder.create(1L, "test-nickName", "test-url", 5.55))
                .build();

        Review review2 = Review.builder()
                .content("test-content2")
                .genre("test-genre")
                .program(Program.builder().id(1L).title("test-title").build())
                .rating(5.55)
                .user(testUserBuilder.create(1L, "test-nickName", "test-url", 5.55))
                .build();
        Review review3 = Review.builder()
                .content("test-content3")
                .genre("test-genre")
                .program(Program.builder().id(1L).title("test-title").build())
                .rating(5.55)
                .user(testUserBuilder.create(1L, "test-nickName", "test-url", 5.55))
                .build();
        Review review4 = Review.builder()
                .content("test-content4")
                .genre("test-genre")
                .program(Program.builder().id(1L).title("test-title").build())
                .rating(5.55)
                .user(testUserBuilder.create(1L, "test-nickName", "test-url", 5.55))
                .build();
        Review review5 = Review.builder()
                .content("test-content5")
                .genre("test-genre")
                .program(Program.builder().id(1L).title("test-title").build())
                .rating(5.55)
                .user(testUserBuilder.create(1L, "test-nickName", "test-url", 5.55))
                .build();

        review1.setCreatedAt(LocalDateTime.now().minusDays(5));
        review2.setCreatedAt(LocalDateTime.now().minusDays(2));
        review3.setCreatedAt(LocalDateTime.now());
        review4.setCreatedAt(LocalDateTime.now().plusHours(1));
        review5.setCreatedAt(LocalDateTime.now().plusHours(2));

        doNothing().when(reviewService).save(any(Review.class));

        reviewService.save(review1);
        reviewService.save(review2);
        reviewService.save(review3);
        reviewService.save(review4);
        reviewService.save(review5);

        List<Review> list = List.of(review5, review4, review3, review2);

        List<LatestReviewsDTO> response = list.stream().map(review -> LatestReviewsDTO.builder()
                .reviewId(review.getId())
                .programTitle(review.getProgram().getTitle())
                .content(review.getContent())
                .userRating(review.getRating())
                .profilePhoto(review.getUser().getProfilePhoto())
                .nickName(review.getUser().getNickName())
                .build()).collect(Collectors.toList());

        when(reviewService.getLatestReviews()).thenReturn(response);

        //when
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/main/latestReviews"));

        //then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data", response).exists());
    }
}
