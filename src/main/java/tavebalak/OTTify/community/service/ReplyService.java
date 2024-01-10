package tavebalak.OTTify.community.service;

import tavebalak.OTTify.community.dto.request.ReplyCommentCreateDTO;
import tavebalak.OTTify.community.dto.request.ReplyCommentEditDTO;
import tavebalak.OTTify.community.dto.request.ReplyRecommentCreateDTO;
import tavebalak.OTTify.community.dto.request.ReplyRecommentEditDTO;
import tavebalak.OTTify.community.dto.response.CommentDTO;
import tavebalak.OTTify.community.entity.Reply;
import tavebalak.OTTify.error.exception.NotFoundException;

import java.util.List;

public interface ReplyService {
    public Reply saveComment(ReplyCommentCreateDTO c) throws NotFoundException;
    public void saveRecomment(ReplyRecommentCreateDTO c);
    public void modifyComment(ReplyCommentEditDTO c) throws NotFoundException;
    public void modifyRecomment(ReplyRecommentEditDTO c) throws NotFoundException;
    public void deleteComment(Long subjectId, Long commentId) throws NotFoundException;

    public void deleteRecomment(Long subjectId, Long commentId, Long recommentId) throws NotFoundException;

    List<CommentDTO> getComment(Long commentId);
}
