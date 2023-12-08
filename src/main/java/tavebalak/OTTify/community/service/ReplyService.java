package tavebalak.OTTify.community.service;

import tavebalak.OTTify.community.dto.ReplyCommentCreateDTO;
import tavebalak.OTTify.community.dto.ReplyRecommentCreateDTO;

public interface ReplyService {
    public void saveComment(ReplyCommentCreateDTO c);

    public void saveRecomment(ReplyRecommentCreateDTO c);
}
