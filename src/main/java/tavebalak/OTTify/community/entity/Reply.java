package tavebalak.OTTify.community.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import tavebalak.OTTify.common.entity.BaseEntity;
import tavebalak.OTTify.community.dto.response.ReplyCommentEditorDTO;
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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Community community;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Reply parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> child = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
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
    public Reply(Long id, String content, Community community, User user) {
        this.content = content;
        this.user = user;
        addCommunity(community);
    }

    public ReplyCommentEditorDTO toEditor(){
        return new ReplyCommentEditorDTO(content);
    }


    public void edit(ReplyCommentEditorDTO c){
        this.content = c.getComment();
    }
}
