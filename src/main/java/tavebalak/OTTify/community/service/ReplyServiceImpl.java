package tavebalak.OTTify.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tavebalak.OTTify.community.dto.CommunitySubjectEditorDTO;
import tavebalak.OTTify.community.dto.ReplyCommentCreateDTO;
import tavebalak.OTTify.community.dto.ReplyCommentEditDTO;
import tavebalak.OTTify.community.dto.ReplyRecommentCreateDTO;
import tavebalak.OTTify.community.entity.Community;
import tavebalak.OTTify.community.entity.Reply;
import tavebalak.OTTify.community.repository.CommunityRepository;
import tavebalak.OTTify.community.repository.ReplyRepository;
import tavebalak.OTTify.exception.ErrorCode;
import tavebalak.OTTify.exception.NotFoundException;
import tavebalak.OTTify.program.entity.Program;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{


    private final CommunityRepository communityRepository;
    private final ReplyRepository replyRepository;
    @Override
    public void saveComment(ReplyCommentCreateDTO c) {
        replyRepository.save(
                Reply.builder()
                .community(communityRepository.findById(c.getSubjectId()).get())
                .content(c.getComment())
                .build());
    }

    @Override
    public void saveRecomment(ReplyRecommentCreateDTO c) {
        Reply reply = replyRepository.save(
                Reply.builder()
                .community(communityRepository.findById(c.getSubjectId()).get())
                .content(c.getContent())
                .build());

        Reply parentReply = replyRepository.findById(c.getCommentId()).get();
        parentReply.addReply(reply);
    }

    @Override
    public void modifyComment(ReplyCommentEditDTO c) {
        


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
}
