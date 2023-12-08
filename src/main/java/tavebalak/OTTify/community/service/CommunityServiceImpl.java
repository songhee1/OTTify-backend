package tavebalak.OTTify.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tavebalak.OTTify.community.dto.*;
import tavebalak.OTTify.community.entity.Community;
import tavebalak.OTTify.community.repository.CommunityRepository;
import tavebalak.OTTify.exception.ErrorCode;
import tavebalak.OTTify.exception.NotFoundException;
import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.program.repository.ProgramRepository;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public CommunitySubjectsDTO findAllSubjects(Pageable pageable) {
        Page<Community> communities = communityRepository.findCommunitiesBy(pageable);
        List<CommunitySubjectsListDTO> listDTO = communities.stream().map(
                community -> CommunitySubjectsListDTO
                        .builder()
                        .createdDate(community.getCreatedDate())
                        .modifiedDate(community.getModifiedDate())
                        .title(community.getTitle())
                        .content(community.getContent())
                        .nickName(community.getUser().getNickName())
                        .programId(community.getProgram().getId())
                        .build()
        ).collect(Collectors.toList());

        //communities 하나하나에 접근 -> DTO 명세서 대로 설정 -> DTO 설


        return  CommunitySubjectsDTO.builder().subjectAmount(communities.getTotalElements()).list(listDTO).build();
    }
}
