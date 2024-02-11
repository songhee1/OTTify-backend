package tavebalak.OTTify.community.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import tavebalak.OTTify.community.dto.request.ReplyCommentCreateDTO;
import tavebalak.OTTify.community.dto.request.ReplyCommentEditDTO;
import tavebalak.OTTify.community.dto.request.ReplyRecommentCreateDTO;
import tavebalak.OTTify.community.dto.request.ReplyRecommentEditDTO;
import tavebalak.OTTify.community.dto.response.CommentDTO;
import tavebalak.OTTify.community.dto.response.ReplyCommentEditorDTO;
import tavebalak.OTTify.community.entity.Community;
import tavebalak.OTTify.community.entity.Reply;
import tavebalak.OTTify.community.repository.CommunityRepository;
import tavebalak.OTTify.community.repository.ReplyRepository;
import tavebalak.OTTify.error.ErrorCode;
import tavebalak.OTTify.error.exception.BadRequestException;
import tavebalak.OTTify.error.exception.NotFoundException;
import tavebalak.OTTify.error.exception.UnauthorizedException;
import tavebalak.OTTify.oauth.jwt.SecurityUtil;
import tavebalak.OTTify.user.entity.User;
import tavebalak.OTTify.user.repository.LikedReplyRepository;
import tavebalak.OTTify.user.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
@Validated
public class ReplyServiceImpl implements ReplyService {

    private final CommunityRepository communityRepository;
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;
    private final LikedReplyRepository likedReplyRepository;

    @Override
    public Reply saveComment(ReplyCommentCreateDTO c) {
        Community community = communityRepository.findById(c.getSubjectId()).orElseThrow(
            () -> new NotFoundException(ErrorCode.COMMUNITY_NOT_FOUND)
        );

        return replyRepository.save(Reply.builder()
            .community(community)
            .content(c.getComment())
            .user(getUser())
            .build());
    }

    @Override
    public void saveRecomment(ReplyRecommentCreateDTO c) {

        boolean hasParent = replyRepository.existsByIdAndParentId(c.getCommentId(), null);
        if (!hasParent) {
            throw new NotFoundException(ErrorCode.REPLY_NOT_FOUND);
        }

        Reply reply = replyRepository.save(Reply.builder()
            .community(communityRepository.findById(c.getSubjectId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.COMMUNITY_NOT_FOUND)))
            .content(c.getContent())
            .user(getUser())
            .build());

        Reply parentReply = replyRepository.findById(c.getCommentId()).get();
        parentReply.addReply(reply);
    }

    @Override
    public void modifyComment(ReplyCommentEditDTO c) {
        boolean present = communityRepository.findById(c.getSubjectId()).isPresent();
        if (!present) {
            throw new NotFoundException(ErrorCode.COMMUNITY_NOT_FOUND);
        }

        Reply savedReply = replyRepository.findById(c.getCommentId())
            .orElseThrow(() -> new NotFoundException(ErrorCode.REPLY_NOT_FOUND));

        if (!Objects.equals(savedReply.getUser().getId(), getUser().getId())) {
            throw new BadRequestException(ErrorCode.CAN_NOT_OTHER_COMMENT_REWRITE_REQUEST);
        }

        ReplyCommentEditorDTO replyCommentEditorDTOBuilder = savedReply.toEditor();
        ReplyCommentEditorDTO edit = replyCommentEditorDTOBuilder.changeComment(c.getComment());

        savedReply.edit(edit);

    }

    @Override
    public void modifyRecomment(ReplyRecommentEditDTO c) {

        if (communityRepository.findById(c.getSubjectId()).isEmpty()) {
            throw new NotFoundException(ErrorCode.COMMUNITY_NOT_FOUND);
        }
        if (replyRepository.findById(c.getCommentId()).isEmpty()) {
            throw new NotFoundException(ErrorCode.REPLY_NOT_FOUND);
        }

        Reply savedReply = replyRepository.findById(c.getRecommentId()).orElseThrow(
            () -> new NotFoundException(ErrorCode.REPLY_NOT_FOUND)
        );

        if (!Objects.equals(savedReply.getUser().getId(), getUser().getId())) {
            throw new BadRequestException(ErrorCode.CAN_NOT_OTHER_COMMENT_REWRITE_REQUEST);
        }

        ReplyCommentEditorDTO reReplyCommentEditorDTOBuilder = savedReply.toEditor();
        ReplyCommentEditorDTO build = reReplyCommentEditorDTOBuilder.changeComment(c.getContent());

        savedReply.edit(build);
    }

    @Override
    public void deleteComment(Long subjectId, Long commentId) {
        communityRepository.findById(subjectId).orElseThrow(
            () -> new NotFoundException(ErrorCode.COMMUNITY_NOT_FOUND)
        );

        Reply savedReply = replyRepository.findById(commentId).orElseThrow(
            () -> new NotFoundException(ErrorCode.REPLY_NOT_FOUND)
        );

        if (!Objects.equals(savedReply.getUser().getId(), getUser().getId())) {
            throw new BadRequestException(ErrorCode.CAN_NOT_OTHER_COMMENT_DELETE_REQUEST);
        }
        likedReplyRepository.deleteAllByReply(savedReply);
        replyRepository.delete(savedReply);

    }

    @Override
    public void deleteRecomment(Long recommentId) {

        Reply savedReply = replyRepository.findById(recommentId).orElseThrow(
            () -> new NotFoundException(ErrorCode.REPLY_NOT_FOUND)
        );

        if (!Objects.equals(savedReply.getUser().getId(), getUser().getId())) {
            throw new BadRequestException(ErrorCode.CAN_NOT_OTHER_COMMENT_DELETE_REQUEST);
        }
        likedReplyRepository.deleteAllByReply(savedReply);
        replyRepository.delete(savedReply);
    }

    @Override
    public List<CommentDTO> getComment(Long commentId) {
        List<Reply> listComment = replyRepository.findByIdAndParentId(commentId, null);
        return listComment.stream().map(comment ->
            CommentDTO.builder().commentId(comment.getId())
                .content(comment.getContent())
                .subjectId(comment.getCommunity().getId())
                .build()).collect(Collectors.toList());
    }

    public User getUser() {
        return userRepository.findByEmail(
                SecurityUtil.getCurrentEmail().get())
            .orElseThrow(() -> new UnauthorizedException(ErrorCode.UNAUTHORIZED)
            );
    }

}
