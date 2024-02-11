package tavebalak.OTTify.community.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import tavebalak.OTTify.common.entity.BaseEntity;
import tavebalak.OTTify.community.dto.response.ReplyCommentEditorDTO;
import tavebalak.OTTify.user.entity.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @BatchSize(size = 100)
    private List<Reply> child = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    private int likeCount;

    public void addReply(Reply reply) {
        this.child.add(reply);
        reply.addParent(this);
    }

    public void addParent(Reply reply) {
        this.parent = reply;
    }

    public void addCommunity(Community community) {
        this.community = community;
        community.getReplyList().add(this);
    }

    @Builder
    public Reply(Long id, String content, Community community, User user) {
        this.content = content;
        this.user = user;
        addCommunity(community);
        this.likeCount = 0;
    }

    public ReplyCommentEditorDTO toEditor() {
        return new ReplyCommentEditorDTO(content);
    }

    public void edit(ReplyCommentEditorDTO c) {
        this.content = c.getComment();
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }
}
