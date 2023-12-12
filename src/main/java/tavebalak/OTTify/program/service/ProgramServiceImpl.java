package tavebalak.OTTify.program.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tavebalak.OTTify.genre.entity.UserGenre;
import tavebalak.OTTify.genre.entity.repository.UserGenreRepository;
import tavebalak.OTTify.program.dto.RecommendProgramsDTO;
import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.program.repository.ProgramRepository;
import tavebalak.OTTify.user.entity.LikedProgram;
import tavebalak.OTTify.user.repository.LikedProgramRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProgramServiceImpl implements  ProgramService {
    private final UserGenreRepository userGenreRepository;
    private final LikedProgramRepository likedProgramRepository;
    private final ProgramRepository programRepository;

    public RecommendProgramsDTO getRecommendProgram(){
        //1순위 장르가 같은 사람들의 UserGenre 리스트 조회
        UserGenre byGenreIdAndIsFirst = userGenreRepository.findByUserIdAndIsFirst(1L, true);
        List<UserGenre> userGenreList = userGenreRepository.findByGenreId(byGenreIdAndIsFirst.getGenre().getId());

        //UserGenre 중 사용자를 제외한 사람들로 좁힘
        List<UserGenre> collect = userGenreList.stream().filter(userGenre -> userGenre.getUser().getId() != 1L).collect(Collectors.toList());

        Set<Program> programSet = new HashSet<>();

        for (UserGenre userGenre : collect) {
            List<LikedProgram> likedPrograms = likedProgramRepository.findByUserId(userGenre.getUser().getId());
            likedPrograms.forEach(userLikedProgram-> programSet.add(userLikedProgram.getProgram()));
        }

        //2순위 장르가 같은 사람들의 UserGenre 리스트 조회
        UserGenre byUserIdAndIsFirst = userGenreRepository.findByUserIdAndIsFirst(1L, false);
        List<UserGenre> userGenreList1 = userGenreRepository.findByGenreId(byUserIdAndIsFirst.getGenre().getId());

        //UserGenre 중 사용자 제외 사람들로 좁힘
        List<UserGenre> collect1 = userGenreList1.stream().filter(userGenre -> userGenre.getUser().getId() != 1L).collect(Collectors.toList());
        Set<Program> programSet1 = new HashSet<>();

        for (UserGenre userGenre : collect1) {
            List<LikedProgram> likedPrograms = likedProgramRepository.findByUserId(userGenre.getUser().getId());
            likedPrograms.forEach(userLikedProgram-> programSet1.add(userLikedProgram.getProgram()));
        }

        //별점 높은 순으로 10개 리스트 조회
        List<Program> top10ByOrderByAverageRatingDesc = programRepository.findTop10ByOrderByAverageRatingDesc();

        
        return null;
    }
}
