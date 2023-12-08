package tavebalak.OTTify.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tavebalak.OTTify.community.dto.ReplyCommentCreateDTO;
import tavebalak.OTTify.community.dto.ReplyRecommentCreateDTO;
import tavebalak.OTTify.community.entity.Reply;
import tavebalak.OTTify.community.repository.CommunityRepository;
import tavebalak.OTTify.community.repository.ReplyRepository;
@Service
@Transactional
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{


    private final CommunityRepository communityRepository;
    private final ReplyRepository replyRepository;
    @Override
    public void saveComment(ReplyCommentCreateDTO c) {
        replyRepository.save(Reply.builder()
                .community(communityRepository.findById(c.getSubjectId()).get())
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
}
