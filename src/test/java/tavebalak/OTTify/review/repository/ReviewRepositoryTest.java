package tavebalak.OTTify.review.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import tavebalak.OTTify.common.constant.Role;
import tavebalak.OTTify.common.constant.SocialType;
import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.program.repository.ProgramRepository;
import tavebalak.OTTify.review.entity.Review;
import tavebalak.OTTify.user.entity.User;
import tavebalak.OTTify.user.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE,
    connection = EmbeddedDatabaseConnection.H2)
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProgramRepository programRepository;

    User.TestUserBuilder testUserBuilder = User.testUserBuilder();


    @Test
    @DisplayName("리뷰가 DB 저장이 잘 되었는지 확인")
    public void addReview() throws Exception {
        //given
        Program savedProgram = programRepository.save(
            Program.testBuilder().id(1L).title("test-title").build());
        User savedUser = userRepository.save(
            User.builder()
                .email("test-email")
                .nickName("test-nickName")
                .profilePhoto("test-url")
                .socialType(SocialType.GOOGLE) // 적절한 값으로 변경
                .role(Role.USER) // 적절한 값으로 변경
                .build()
        );
        Review review = Review.builder()
            .content("test-content")
            .genre("test-genre")
            .program(savedProgram)
            .rating(5.55)
            .user(savedUser)
            .build();

        //when
        Review savedReview = reviewRepository.save(review);

        //then
        assertThat(savedReview.getContent()).isEqualTo("test-content");
        assertThat(savedReview.getRating()).isEqualTo(5.55);
        assertThat(savedReview.getProgram().getTitle()).isEqualTo("test-title");
        assertThat(savedReview.getGenre()).isEqualTo("test-genre");
        assertThat(review).isSameAs(savedReview);
        assertThat(review.getUser().getId()).isEqualTo(savedReview.getUser().getId());
        assertThat(savedReview.getId()).isNotNull();
    }

    @Test
    @DisplayName("저장된 리뷰 중 최신 리뷰 조회 확인")
    public void findLatestReview() throws Exception {
        //given
        User savedUser = userRepository.save(
            User.builder()
                .email("test-email")
                .nickName("test-nickName")
                .profilePhoto("test-url")
                .socialType(SocialType.GOOGLE) // 적절한 값으로 변경
                .role(Role.USER) // 적절한 값으로 변경
                .build()
        );

        Program savedProgram = programRepository.save(
            Program.testBuilder().id(1L).title("test-title").build());

        Review savedReview1 = Review.builder()
            .content("test-content1")
            .genre("test-genre")
            .program(savedProgram)
            .rating(5.55)
            .user(savedUser)
            .build();
        Review savedReview2 = Review.builder()
            .content("test-content2")
            .genre("test-genre")
            .program(savedProgram)
            .rating(5.55)
            .user(savedUser)
            .build();
        Review savedReview3 = Review.builder()
            .content("test-content3")
            .genre("test-genre")
            .program(savedProgram)
            .rating(5.55)
            .user(savedUser)
            .build();
        Review savedReview4 = Review.builder()
            .content("test-content4")
            .genre("test-genre")
            .program(savedProgram)
            .rating(5.55)
            .user(savedUser)
            .build();
        Review savedReview5 = Review.builder()
            .content("test-content5")
            .genre("test-genre")
            .program(savedProgram)
            .rating(5.55)
            .user(savedUser)
            .build();

        reviewRepository.save(savedReview1);
        reviewRepository.save(savedReview2);
        reviewRepository.save(savedReview3);
        reviewRepository.save(savedReview4);
        reviewRepository.save(savedReview5);

        //when
        List<Review> allReviews = reviewRepository.findAll(
            Sort.by(Sort.Direction.DESC, "createdAt"));

        //then
        assertThat(allReviews.get(0).getContent()).isEqualTo("test-content1");
        assertThat(allReviews.get(1).getContent()).isEqualTo("test-content2");
        assertThat(allReviews.get(2).getContent()).isEqualTo("test-content3");
        assertThat(allReviews.get(3).getContent()).isEqualTo("test-content4");
        assertThat(allReviews.get(4).getContent()).isEqualTo("test-content5");
    }
}
