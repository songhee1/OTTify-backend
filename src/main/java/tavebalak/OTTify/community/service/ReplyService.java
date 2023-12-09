package tavebalak.OTTify.community.service;

import tavebalak.OTTify.community.dto.ReplyCommentCreateDTO;
import tavebalak.OTTify.community.dto.ReplyCommentModifyDTO;
import tavebalak.OTTify.community.dto.ReplyRecommentCreateDTO;
import tavebalak.OTTify.exception.NotFoundException;

public interface ReplyService {
    public void saveComment(ReplyCommentCreateDTO c);
    public void saveRecomment(ReplyRecommentCreateDTO c);

    public void modifyComment(ReplyCommentModifyDTO c) throws NotFoundException;
}
