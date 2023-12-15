package tavebalak.OTTify.program.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tavebalak.OTTify.genre.entity.UserGenre;
import tavebalak.OTTify.genre.entity.repository.UserGenreRepository;
import tavebalak.OTTify.program.dto.RecommendProgramsDTO;
import tavebalak.OTTify.program.dto.ServiceListsDTO;
import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.program.repository.ProgramRepository;
import tavebalak.OTTify.user.entity.LikedProgram;
import tavebalak.OTTify.user.repository.LikedProgramRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
public class ProgramServiceImpl implements  ProgramService {
    private final UserGenreRepository userGenreRepository;
    private final LikedProgramRepository likedProgramRepository;
    private final ProgramRepository programRepository;


    public RecommendProgramsDTO getRecommendProgram(int count){
        //1순위 장르가 같은 사람들의 UserGenre 리스트 조회
        UserGenre byGenreIdAndIsFirst = userGenreRepository.findByUserIdAndIsFirst(1L, true);
        List<UserGenre> userGenreList = userGenreRepository.findByGenreId(byGenreIdAndIsFirst.getGenre().getId());

        //UserGenre 중 사용자를 제외한 사람들로 좁힘
        List<UserGenre> collect = userGenreList.stream().filter(userGenre -> userGenre.getUser().getId() != 1L).collect(Collectors.toList());

        Set<Program> GenreRecommendPrograms = new HashSet<>();
        AtomicInteger firstGenreProgramsSize = new AtomicInteger((int) (count * 0.5));
        for (UserGenre userGenre : collect) {
            List<LikedProgram> likedPrograms = likedProgramRepository.findByUserId(userGenre.getUser().getId());
            likedPrograms.forEach(userLikedProgram-> {
                if(firstGenreProgramsSize.get() == 0) return;
                if(!GenreRecommendPrograms.contains(userLikedProgram)) {
                    GenreRecommendPrograms.add(userLikedProgram.getProgram());
                    firstGenreProgramsSize.getAndDecrement();
                }
            });
        }


        //2순위 장르가 같은 사람들의 UserGenre 리스트 조회
        UserGenre byUserIdAndIsFirst = userGenreRepository.findByUserIdAndIsFirst(1L, false);
        List<UserGenre> userGenreList1 = userGenreRepository.findByGenreId(byUserIdAndIsFirst.getGenre().getId());

        //UserGenre 중 사용자 제외 사람들로 좁힘
        List<UserGenre> collect1 = userGenreList1.stream().filter(userGenre -> userGenre.getUser().getId() != 1L).collect(Collectors.toList());
        AtomicInteger secondGenreSize = new AtomicInteger((int) (count * 0.2));
        for (UserGenre userGenre : collect1) {
            List<LikedProgram> likedPrograms = likedProgramRepository.findByUserId(userGenre.getUser().getId());
            likedPrograms.forEach(userLikedProgram-> {
                if(secondGenreSize.get() == 0) return;
                if(!GenreRecommendPrograms.contains(userLikedProgram)){
                    GenreRecommendPrograms.add(userLikedProgram.getProgram());
                    secondGenreSize.getAndDecrement();
                }
            });
        }

        //찜으로 저장한 프로그램 리스트 조회
        AtomicInteger likedProgramsSize = new AtomicInteger((int) (count*0.1));
        likedProgramRepository.findByUserId(1L).forEach(likedProgram ->
                {
                    if(likedProgramsSize.get() == 0) return;
                    if(!GenreRecommendPrograms.contains(likedProgram)){
                        GenreRecommendPrograms.add(programRepository.findById(likedProgram.getProgram().getId()).get());
                        likedProgramsSize.getAndDecrement();
                    }
                });

        //별점 높은 순으로 2개 리스트 조회

        return RecommendProgramsDTO.builder()
                .recommentAmount(count)
                .serviceListsDTOList(
                        GenreRecommendPrograms.stream().map(program ->
                        ServiceListsDTO.builder()
                                .programId(program.getId())
                                .title(program.getTitle())
                                .posterPath(program.getPosterPath())
                                .averageRating(program.getAverageRating())
                                .build()).collect(Collectors.toList())
                ).build();

    }
}
