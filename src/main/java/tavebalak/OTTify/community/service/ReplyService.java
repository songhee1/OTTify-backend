package tavebalak.OTTify.community.service;

import tavebalak.OTTify.community.dto.ReplyCommentCreateDTO;
import tavebalak.OTTify.community.dto.ReplyCommentModifyDTO;
import tavebalak.OTTify.community.dto.ReplyRecommentCreateDTO;
import tavebalak.OTTify.community.dto.ReplyRecommentModifyDTO;
import tavebalak.OTTify.exception.NotFoundException;

public interface ReplyService {
    public void saveComment(ReplyCommentCreateDTO c) throws NotFoundException;
    public void saveRecomment(ReplyRecommentCreateDTO c);
    public void modifyComment(ReplyCommentModifyDTO c) throws NotFoundException;
    public void modifyRecomment(ReplyRecommentModifyDTO c) throws NotFoundException;
    public void deleteComment(Long subjectId, Long commentId) throws NotFoundException;
}
