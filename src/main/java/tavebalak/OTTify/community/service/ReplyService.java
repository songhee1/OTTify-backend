package tavebalak.OTTify.community.service;

import tavebalak.OTTify.community.dto.ReplyCommentCreateDTO;

public interface ReplyService {
    public void saveComment(ReplyCommentCreateDTO c);
}
