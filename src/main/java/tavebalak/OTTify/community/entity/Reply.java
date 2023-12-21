package tavebalak.OTTify.community.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tavebalak.OTTify.common.BaseEntity;
import tavebalak.OTTify.community.dto.ReplyCommentEditorDTO;
import tavebalak.OTTify.user.entity.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "reply_id")
    private Long id;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Reply parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> child = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void addReply(Reply reply){
        this.child.add(reply);
        reply.addParent(this);
    }

    public void addParent(Reply reply){
        this.parent = reply;
    }

    public void addCommunity(Community community){
        this.community = community;
        community.getReplyList().add(this);
    }

    @Builder
    public Reply(String content, Community community) {
        this.content = content;
        addCommunity(community);
    }

    public ReplyCommentEditorDTO.ReplyCommentEditorDTOBuilder toEditor(){
        return ReplyCommentEditorDTO.builder()
                .comment(content);
    }


    public void edit(ReplyCommentEditorDTO c){
        this.content = c.getComment();
    }
}
