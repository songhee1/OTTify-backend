package tavebalak.OTTify.community.service;

import java.util.List;
import tavebalak.OTTify.community.dto.request.ReplyCommentCreateDTO;
import tavebalak.OTTify.community.dto.request.ReplyCommentEditDTO;
import tavebalak.OTTify.community.dto.request.ReplyRecommentCreateDTO;
import tavebalak.OTTify.community.dto.request.ReplyRecommentEditDTO;
import tavebalak.OTTify.community.dto.response.CommentDTO;
import tavebalak.OTTify.community.entity.Reply;

public interface ReplyService {

    Reply saveComment(ReplyCommentCreateDTO c);

    void saveRecomment(ReplyRecommentCreateDTO c);

    void modifyComment(ReplyCommentEditDTO c);

    void modifyRecomment(ReplyRecommentEditDTO c);

    void deleteComment(Long subjectId, Long commentId);

    void deleteRecomment(Long subjectId, Long commentId, Long recommentId);

    List<CommentDTO> getComment(Long commentId);
}
