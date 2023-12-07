package tavebalak.OTTify.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tavebalak.OTTify.community.dto.CommunitySubjectCreateDTO;
import tavebalak.OTTify.community.dto.CommunitySubjectEditDTO;
import tavebalak.OTTify.community.dto.CommunitySubjectEditorDTO;
import tavebalak.OTTify.community.entity.Community;
import tavebalak.OTTify.community.repository.CommunityRepository;
import tavebalak.OTTify.exception.ErrorCode;
import tavebalak.OTTify.exception.NotFoundException;
import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.program.repository.ProgramRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService{
    private final CommunityRepository communityRepository;
    private final ProgramRepository programRepository;
    @Override
    public void saveSubject(CommunitySubjectCreateDTO c){

        boolean present = programRepository.findById(c.getProgramId()).isPresent();
        Program program = null;
        if(!present) {
            program = programRepository.save(Program.builder().id(c.getProgramId()).title(c.getProgramTitle()).posterPath(c.getPosterPath()).build());
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
    @Override
    public void modifySubject(CommunitySubjectEditDTO c) throws NotFoundException {
        Community community = communityRepository.findById(c.getSubjectId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        boolean present = programRepository.findById(c.getProgramId()).isPresent();
        Program program = null;
        if(!present){
            program = programRepository.save(Program.builder().title(c.getProgramTitle()).posterPath(c.getPosterPath()).build());
        }else {
            program = programRepository.findById(c.getProgramId()).get();
        }

        CommunitySubjectEditorDTO.CommunitySubjectEditorDTOBuilder communitySubjectEditorDTOBuilder = community.toEditor();
        CommunitySubjectEditorDTO communitySubjectEditorDTO = communitySubjectEditorDTOBuilder.title(c.getSubjectName()).content(c.getContent()).program(program).build();

        community.edit(communitySubjectEditorDTO);
    }

    @Override
    public void deleteSubject(Long subjectId) throws NotFoundException {
        Community community = communityRepository.findById(subjectId).orElseThrow(() -> new NotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        communityRepository.delete(community);
    }
}
