package tavebalak.OTTify.program.service;

import lombok.RequiredArgsConstructor;
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

    public RecommendProgramsDTO getRecommendProgram(){
        //1순위 장르가 같은 사람들의 UserGenre 리스트 조회
        List<UserGenre> byGenreIdAndIsFirst = userGenreRepository.findByGenreIdAndIsFirst(1L, true);

        //UserGenre 중 사용자를 제외한 사람들로 좁힘
        List<UserGenre> collect = byGenreIdAndIsFirst.stream().filter(userGenre -> userGenre.getUser().getId() != 1L).collect(Collectors.toList());

        Set<Program> programSet = new HashSet<>();

        for (UserGenre userGenre : collect) {
            List<LikedProgram> likedPrograms = likedProgramRepository.findByUserId(userGenre.getUser().getId());
            likedPrograms.forEach(userLikedProgram-> programSet.add(userLikedProgram.getProgram()));
        }

        return null;
    }
}
