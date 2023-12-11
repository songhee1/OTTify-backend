package tavebalak.OTTify.community.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tavebalak.OTTify.community.dto.*;
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
    public void saveComment(ReplyCommentCreateDTO c) throws NotFoundException {
        Community community = communityRepository.findById(c.getSubjectId()).orElseThrow(
                () -> new NotFoundException(ErrorCode.ENTITY_NOT_FOUND)
        );

        replyRepository.save(Reply.builder()
                .community(community)
                .content(c.getComment())
                .build());
    }
    @Override
    public void saveRecomment(ReplyRecommentCreateDTO c) {

        Reply reply = replyRepository.save(Reply.builder()
                .community(communityRepository.findById(c.getSubjectId()).get())
                .content(c.getContent())
                .build());

        Reply parentReply = replyRepository.findById(c.getCommentId()).get();
        parentReply.addReply(reply);
    }

    @Override
    public void modifyComment(ReplyCommentModifyDTO c) throws NotFoundException {

        Reply reply = replyRepository.findById(c.getCommentId()).orElseThrow(
                () -> new NotFoundException(ErrorCode.ENTITY_NOT_FOUND)
        );
        ReplyCommentEditorDTO.ReplyCommentEditorDTOBuilder replyCommentEditorDTOBuilder = reply.toEditor();
        ReplyCommentEditorDTO edit = replyCommentEditorDTOBuilder.comment(c.getComment()).build();

        reply.edit(edit);

    }

    @Override
    public void modifyRecomment(ReplyRecommentModifyDTO c) throws NotFoundException {
        Reply reReply = replyRepository.findById(c.getRecommentId()).orElseThrow(
                () -> new NotFoundException(ErrorCode.ENTITY_NOT_FOUND)
        );
        ReplyCommentEditorDTO.ReplyCommentEditorDTOBuilder reReplyCommentEditorDTOBuilder = reReply.toEditor();
        ReplyCommentEditorDTO build = reReplyCommentEditorDTOBuilder.comment(c.getContent()).build();

        reReply.edit(build);
    }

    @Override
    public void deleteComment(Long subjectId, Long commentId) throws NotFoundException {
        Community community = communityRepository.findById(subjectId).orElseThrow(
                () -> new NotFoundException(ErrorCode.ENTITY_NOT_FOUND)
        );

        Reply reply = replyRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException(ErrorCode.ENTITY_NOT_FOUND)
        );

        replyRepository.delete(reply);

    }

    @Override
    public void deleteRecomment(Long subjectId, Long commentId, Long recommentId) throws NotFoundException {
        communityRepository.findById(subjectId).orElseThrow(
                () -> new NotFoundException(ErrorCode.ENTITY_NOT_FOUND)
        );
        replyRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException(ErrorCode.ENTITY_NOT_FOUND)
        );
        Reply reply = replyRepository.findById(recommentId).orElseThrow(
                () -> new NotFoundException(ErrorCode.ENTITY_NOT_FOUND)
        );

        replyRepository.delete(reply);
    }

}
