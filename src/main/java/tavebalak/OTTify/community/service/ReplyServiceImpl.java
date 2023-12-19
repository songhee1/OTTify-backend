package tavebalak.OTTify.community.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import tavebalak.OTTify.community.dto.*;
import tavebalak.OTTify.community.entity.Community;
import tavebalak.OTTify.community.entity.Reply;
import tavebalak.OTTify.community.repository.CommunityRepository;
import tavebalak.OTTify.community.repository.ReplyRepository;
import tavebalak.OTTify.exception.ErrorCode;
import tavebalak.OTTify.exception.NotFoundException;
import tavebalak.OTTify.program.entity.Program;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
@Validated
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

        boolean hasParent = replyRepository.existsByIdAndParentId(c.getCommentId(), null);
        if(!hasParent){
            throw new NoSuchElementException(ErrorCode.BAD_REQUEST.getMessage());
        }

        Reply reply = replyRepository.save(Reply.builder()
                .community(communityRepository.findById(c.getSubjectId()).orElseThrow(NoSuchElementException::new))
                .content(c.getContent())
                .build());

        Reply parentReply = replyRepository.findById(c.getCommentId()).get();
        parentReply.addReply(reply);
    }

    @Override
    public void modifyComment(ReplyCommentEditDTO c) throws NotFoundException {
        //subjectId, commentId, comment

        boolean present = communityRepository.findById(c.getSubjectId()).isPresent();
        if(!present){
            throw new NoSuchElementException(ErrorCode.BAD_REQUEST.getMessage());
        }

        Reply reply = replyRepository.findById(c.getCommentId()).orElseThrow(
                () -> new NotFoundException(ErrorCode.ENTITY_NOT_FOUND)
        );
        ReplyCommentEditorDTO.ReplyCommentEditorDTOBuilder replyCommentEditorDTOBuilder = reply.toEditor();
        ReplyCommentEditorDTO edit = replyCommentEditorDTOBuilder.comment(c.getComment()).build();

        reply.edit(edit);

    }

    @Override
    public void modifyRecomment(ReplyRecommentEditDTO c) throws NotFoundException {

        if(!communityRepository.findById(c.getSubjectId()).isPresent()){
            throw new NoSuchElementException(ErrorCode.BAD_REQUEST.getMessage());
        }
        if(!replyRepository.findById(c.getCommentId()).isPresent()){
            throw new NoSuchElementException(ErrorCode.BAD_REQUEST.getMessage());
        }

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
