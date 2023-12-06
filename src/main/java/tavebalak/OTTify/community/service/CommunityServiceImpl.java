package tavebalak.OTTify.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tavebalak.OTTify.community.dto.CommunitySubjectCreateDTO;
import tavebalak.OTTify.community.entity.Community;
import tavebalak.OTTify.community.repository.CommunityRepository;
import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.program.repository.ProgramRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService{
    private final CommunityRepository communityRepository;
    private final ProgramRepository programRepository;

    public void saveSubject(CommunitySubjectCreateDTO c){

        boolean present = programRepository.findById(c.getProgramId()).isPresent();
        Program program = null;
        if(!present) {
            program = programRepository.save(Program.builder().title(c.getProgramTitle()).posterPath(c.getPosterPath()).build());
        }else{
            program = programRepository.findById(c.getProgramId()).get();
        }

        Community community = Community.builder()
                .title(c.getSubjectName())
                .content(c.getContent())
                .program(program)
                .build();

        communityRepository.save(community);

    }
}
