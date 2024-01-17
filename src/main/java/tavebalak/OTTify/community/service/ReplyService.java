package tavebalak.OTTify.community.service;

import java.util.List;
import tavebalak.OTTify.community.dto.request.ReplyCommentCreateDTO;
import tavebalak.OTTify.community.dto.request.ReplyCommentEditDTO;
import tavebalak.OTTify.community.dto.request.ReplyRecommentCreateDTO;
import tavebalak.OTTify.community.dto.request.ReplyRecommentEditDTO;
import tavebalak.OTTify.community.dto.response.CommentDTO;
import tavebalak.OTTify.community.entity.Reply;
import tavebalak.OTTify.error.exception.NotFoundException;

public interface ReplyService {

    Reply saveComment(ReplyCommentCreateDTO c) throws NotFoundException;

    void saveRecomment(ReplyRecommentCreateDTO c);

    void modifyComment(ReplyCommentEditDTO c) throws NotFoundException;

    void modifyRecomment(ReplyRecommentEditDTO c) throws NotFoundException;

    void deleteComment(Long subjectId, Long commentId) throws NotFoundException;

    void deleteRecomment(Long subjectId, Long commentId, Long recommentId) throws NotFoundException;

    List<CommentDTO> getComment(Long commentId);
}
