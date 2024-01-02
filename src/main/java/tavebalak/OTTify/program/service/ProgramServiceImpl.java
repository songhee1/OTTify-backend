package tavebalak.OTTify.program.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tavebalak.OTTify.error.ErrorCode;
import tavebalak.OTTify.error.exception.NotFoundException;
import tavebalak.OTTify.error.exception.UnauthorizedException;
import tavebalak.OTTify.genre.entity.Genre;
import tavebalak.OTTify.genre.entity.ProgramGenre;
import tavebalak.OTTify.genre.entity.UserGenre;
import tavebalak.OTTify.genre.repository.GenreRepository;
import tavebalak.OTTify.genre.repository.ProgramGenreRepository;
import tavebalak.OTTify.genre.repository.UserGenreRepository;
import tavebalak.OTTify.oauth.jwt.SecurityUtil;
import tavebalak.OTTify.program.dto.RecommendProgramsDTO;
import tavebalak.OTTify.program.dto.ServiceListsDTO;
import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.program.repository.ProgramRepository;
import tavebalak.OTTify.user.entity.LikedProgram;
import tavebalak.OTTify.user.entity.User;
import tavebalak.OTTify.user.repository.LikedProgramRepository;
import tavebalak.OTTify.user.repository.UserRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProgramServiceImpl implements  ProgramService {
    private final UserGenreRepository userGenreRepository;
    private final LikedProgramRepository likedProgramRepository;
    private final ProgramRepository programRepository;
    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final ProgramGenreRepository programGenreRepository;


    public RecommendProgramsDTO getRecommendProgram(int count){
        User savedUser = getUser();
        Set<Program> recommendPrograms = new HashSet<>();

        //1순위 장르 리스트 조회
        //1) 사용자의 1순위 UserGenre 추출
        Optional<UserGenre> byGenreIdAndIsFirst = userGenreRepository.findByUserIdAndIsFirst(savedUser.getId(), true);

        if(byGenreIdAndIsFirst.isPresent()) {
            //Genre 추출
            Genre userFirstGenre = genreRepository.findById(byGenreIdAndIsFirst.get().getId()).orElseThrow(
                    () -> new NotFoundException(ErrorCode.ENTITY_NOT_FOUND)
            );

            List<ProgramGenre> programGenreList = programGenreRepository.findByGenreId(userFirstGenre.getId());

            for (int i = 1; i < 4; i++) {
                int idx = new Random().nextInt(programGenreList.size());
                Optional<Program> program = programRepository.findById(programGenreList.get(idx).getId());
                program.ifPresent(recommendPrograms::add);
            }
        }

        //2순위 장르 리스트 조회
        Optional<UserGenre> byUserIdAndIsFirst = userGenreRepository.findByUserIdAndIsFirst(savedUser.getId(), false);
        if(byUserIdAndIsFirst.isPresent()) {
            Genre userSecondGenre = genreRepository.findById(byUserIdAndIsFirst.get().getId()).orElseThrow(
                    () -> new NotFoundException(ErrorCode.ENTITY_NOT_FOUND)
            );

            List<ProgramGenre> programGenreList = programGenreRepository.findByGenreId(userSecondGenre.getId());

            int idx = new Random().nextInt(programGenreList.size());
            Optional<Program> program = programRepository.findById(programGenreList.get(idx).getId());
            program.ifPresent(recommendPrograms::add);
        }

        //찜 리스트 조회
        AtomicInteger likedProgramsSize = new AtomicInteger(1);
        likedProgramRepository.findByUserId(savedUser.getId()).forEach(likedProgram ->
                {
                    if(likedProgramsSize.get() == 0) return;
                    Optional<Program> program = programRepository.findById(likedProgram.getProgram().getId());
                    program.ifPresent(recommendPrograms::add);
                    likedProgramsSize.getAndDecrement();
                });

        //별점 높은 순으로 리스트 조회
        List<Program> programList = programRepository.findTop10ByOrderByAverageRatingDesc();
        int idx = new Random().nextInt(programList.size());
        Optional<Program> program = programRepository.findById(programList.get(idx).getId());
        program.ifPresent(recommendPrograms::add);

        while(recommendPrograms.size()<6){
            Long index = 1L + ((long) (new Random().nextDouble() * (50L - 1L)));
            Optional<Program> findProgram = programRepository.findById(index);
            findProgram.ifPresent(recommendPrograms::add);
        }

        return RecommendProgramsDTO.builder()
                .recommentAmount(count)
                .serviceListsDTOList(
                        recommendPrograms.stream().map(pg ->
                        ServiceListsDTO.builder()
                                .programId(pg.getId())
                                .title(pg.getTitle())
                                .posterPath(pg.getPosterPath())
                                .averageRating(pg.getAverageRating())
                                .build()).collect(Collectors.toList())
                ).build();
    }

    private User getUser() {
        return userRepository.findByEmail(
                SecurityUtil.getCurrentEmail().get()).orElseThrow(()-> new UnauthorizedException(ErrorCode.UNAUTHORIZED)
        );
    }
}
