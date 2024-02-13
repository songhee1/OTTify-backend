package tavebalak.OTTify.review;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import tavebalak.OTTify.genre.entity.Genre;
import tavebalak.OTTify.genre.repository.GenreRepository;
import tavebalak.OTTify.program.dto.response.UserSpecificRatingResponseDto;
import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.program.entity.ProgramType;
import tavebalak.OTTify.program.repository.ProgramRepository;
import tavebalak.OTTify.program.service.ProgramDetailsShowService;
import tavebalak.OTTify.review.dto.reviewrequest.ReviewSaveDto;
import tavebalak.OTTify.review.entity.Review;
import tavebalak.OTTify.review.repository.ReviewRepository;
import tavebalak.OTTify.review.repository.ReviewTagRepository;
import tavebalak.OTTify.review.service.ReviewCUDService;
import tavebalak.OTTify.review.service.ReviewShowProgramDetailService;
import tavebalak.OTTify.review.service.ReviewShowProgramDetailServiceImpl;
import tavebalak.OTTify.review.service.ReviewTagServiceImpl;
import tavebalak.OTTify.user.entity.User;
import tavebalak.OTTify.user.repository.UserRepository;

@SpringBootTest
class ReviewShowProgramDetailServiceImplTest {

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProgramRepository programRepository;

    @Autowired
    ReviewShowProgramDetailServiceImpl reviewShowProgramDetailServiceImpl;

    @Autowired
    ReviewTagServiceImpl reviewTagService;

    @Autowired
    ReviewTagRepository reviewTagRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReviewCUDService reviewCUDService;

    @Autowired
    ReviewShowProgramDetailService reviewShowProgramDetailService;

    @Autowired
    ProgramDetailsShowService programDetailsShowService;

    //프로그램 별 장르 별점의 변화와 전체 장르 별점의 변화를 테스트하고
    //user 의 평균 별점 변화를 테스트 합니다.

    @Test
    @Rollback(value = true)
    @Transactional
    void saveReview() {
        User user = makeUser();
        Program program = makeProgram();

        List<Long> reviewTagIdDtoList = makeReviewTag();

        ReviewSaveDto reviewSaveDto1 = new ReviewSaveDto("아 진짜 꿀잼이였어요ㅠ", program.getId(), 3.5,
            reviewTagIdDtoList);

        reviewCUDService.saveReview(user, reviewSaveDto1);

        Review findReview = reviewRepository.findByProgramIdAndUserId(program.getId(), user.getId())
            .get();

        Assertions.assertThat(findReview.getContent()).isEqualTo("아 진짜 꿀잼이였어요ㅠ");

        Assertions.assertThat(program.getReviewCount()).isEqualTo(1);
        Assertions.assertThat(program.getAverageRating()).isEqualTo(3.5);

        User user2 = makeUser2();

        ReviewSaveDto reviewSaveDto2 = new ReviewSaveDto("애니메이션 장르 좋아하는데 꿀잼이였어요~", program.getId(),
            4.5, reviewTagIdDtoList);

        reviewCUDService.saveReview(user2, reviewSaveDto2);

        Review findReview2 = reviewRepository.findByProgramIdAndUserId(program.getId(),
            user2.getId()).get();

        Assertions.assertThat(program.getReviewCount()).isEqualTo(2);
        Assertions.assertThat(program.getAverageRating()).isEqualTo(4);

        User user3 = makeUser3();

        ReviewSaveDto reviewSaveDto3 = new ReviewSaveDto("요즘 애니메이션에 빠져서 그런가 재밌네", program.getId(),
            4,
            reviewTagIdDtoList);

        reviewCUDService.saveReview(user3, reviewSaveDto3);

        Assertions.assertThat(program.getReviewCount()).isEqualTo(3);
        Assertions.assertThat(program.getAverageRating()).isEqualTo(4);

        Review findReview3 = reviewRepository.findByProgramIdAndUserId(program.getId(),
            user3.getId()).get();

        User user4 = makeUser4();
        ReviewSaveDto reviewSaveDto4 = new ReviewSaveDto("범죄물이 아니였어? 좀 노잼", program.getId(), 1.5,
            reviewTagIdDtoList);
        reviewCUDService.saveReview(user4, reviewSaveDto4);

        //user의 평균 별점 변화 확인

        Program program2 = makeProgram2();

        ReviewSaveDto reviewSaveDto5 = new ReviewSaveDto("공포는 취향이 아니라서 ㅋ", program2.getId(), 2,
            reviewTagIdDtoList);

        reviewCUDService.saveReview(user, reviewSaveDto5);

        Assertions.assertThat(user.getUserReviewCounts()).isEqualTo(2);
        Assertions.assertThat(user.getAverageRating()).isEqualTo(2.75);

        //좋아요 확인 기능

        reviewCUDService.likeReview(user, findReview3.getId());

        Assertions.assertThat(findReview3.getLikeCounts()).isEqualTo(1);

        reviewCUDService.likeReview(user, findReview3.getId());

        Assertions.assertThat(findReview3.getLikeCounts()).isEqualTo(0);

        reviewCUDService.likeReview(user, findReview3.getId());

        Assertions.assertThat(findReview3.getLikeCounts()).isEqualTo(1);

        UserSpecificRatingResponseDto userSpecificRatingResponseDto = programDetailsShowService.showUserSpecificRating(
            user, program.getId());
        Assertions.assertThat(userSpecificRatingResponseDto.getUsersFirstGenreProgramRating())
            .isEqualTo("4.0");


    }

    @Transactional
    User makeUser() {
        User user = User.builder()
            .email("dionisos1998@naver.com")
            .build();

        Genre genre1 = genreRepository.findByTmDbGenreId(16L).get();
        Genre genre2 = genreRepository.findByTmDbGenreId(35L).get();

        user.addGenre(genre1, true);
        user.addGenre(genre2, false);
        userRepository.save(user);
        return user;
        //애니메이션를 firstGenre로 두고, 애니메이션을 후 순위로 둠
    }

    @Transactional
    User makeUser2() {
        User user = User.builder()
            .email("dionisos1988@gmail.com")
            .build();

        Genre genre1 = genreRepository.findByTmDbGenreId(16L).get();
        Genre genre2 = genreRepository.findByTmDbGenreId(99L).get();

        user.addGenre(genre1, true);
        user.addGenre(genre2, false);
        userRepository.save(user);
        return user;
        //애니메이션을 firstGenre로 둔 유저, 다큐멘터리 장르를 후순위로 둔 유저
    }

    @Transactional
    User makeUser3() {
        User user = User.builder()
            .email("dionisos198@daum.com")
            .build();

        Genre genre = genreRepository.findByTmDbGenreId(16L).get();//애니메이션를 first Genre 로 둠
        Genre genre2 = genreRepository.findByTmDbGenreId(28L).get();//액션을 둘째 장르로 둠

        user.addGenre(genre, true);
        user.addGenre(genre, false);
        userRepository.save(user);
        return user;
        //애니메이션를 첫번째 액션을 둘째 장르로 두었다.
    }

    @Transactional
    User makeUser4() {
        User user = User.builder()
            .email("dionisos198888@daum.com")
            .build();

        Genre genre = genreRepository.findByTmDbGenreId(28L).get();//액션 first Genre 로 둠
        Genre genre2 = genreRepository.findByTmDbGenreId(16L).get();//애니메이션을 둘째 장르로 둠

        user.addGenre(genre, true);
        user.addGenre(genre, false);
        userRepository.save(user);
        return user;
        //애니메이션를 첫번째 액션을 둘째 장르로 두었다.
    }

    @Transactional
    Program makeProgram() {
        Program program = Program.builder()
            .tmDbProgramId(140L)
            .title("액션과 애니메이션과 코미디가 있는 영화")
            .type(ProgramType.Movie)
            .createdYear("1024")
            .build();

        Genre genre1 = genreRepository.findByTmDbGenreId(28L).get();//액션
        Genre genre2 = genreRepository.findByTmDbGenreId(16L).get();//애니메이션
        Genre genre3 = genreRepository.findByTmDbGenreId(35L).get();//코미디

        program.addGenre(genre1);
        program.addGenre(genre2);
        program.addGenre(genre3);

        programRepository.save(program);

        return program;
    }

    @Transactional
    Program makeProgram2() {
        Program program = Program.builder()
            .tmDbProgramId(150L)
            .title("공포와 미스터리와 SF 가 있는 TV")
            .type(ProgramType.TV)
            .createdYear("1025")
            .build();

        Genre genre1 = genreRepository.findByTmDbGenreId(27L).get();//공포
        Genre genre2 = genreRepository.findByTmDbGenreId(9648L).get();//미스터리
        Genre genre3 = genreRepository.findByTmDbGenreId(878L).get();//SF

        program.addGenre(genre1);
        program.addGenre(genre2);
        program.addGenre(genre3);

        programRepository.save(program);

        return program;
    }

    @Transactional
    List<Long> makeReviewTag() {

        List<Long> reviewTagIdDtoList = new ArrayList<>();

        reviewTagRepository.findAll().forEach(rt -> {
            reviewTagIdDtoList.add(rt.getId());
        });

        return reviewTagIdDtoList;

    }


}
