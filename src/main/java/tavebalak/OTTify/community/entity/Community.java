package tavebalak.OTTify.community.entity;

import java.util.ArrayList;
import java.util.List;
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
import tavebalak.OTTify.common.entity.BaseEntity;
import tavebalak.OTTify.community.dto.response.CommunitySubjectEditorDTO;
import tavebalak.OTTify.community.dto.response.CommunitySubjectImageEditorDTO;
import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.user.entity.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Community extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id")
    private Long id;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;

    @OneToMany(mappedBy = "community", orphanRemoval = true)
    @BatchSize(size = 100)
    private List<Reply> replyList = new ArrayList<>();

    private String imageUrl;

    private int likeCount;

    private int commentCount;

    @Builder
    public Community(Long id, String title, String content, Program program, User user,
        String imageUrl) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.program = program;
        this.user = user;
        this.imageUrl = imageUrl;
        this.likeCount = 0;
        this.commentCount = 0;
    }

    public CommunitySubjectEditorDTO toEditor() {
        return new CommunitySubjectEditorDTO(title, content);
    }

    public CommunitySubjectImageEditorDTO toImageEdior() {
        return new CommunitySubjectImageEditorDTO(title, content, imageUrl);
    }

    public void edit(CommunitySubjectEditorDTO communitySubjectEditorDTO) {
        this.title = communitySubjectEditorDTO.getTitle();
        this.content = communitySubjectEditorDTO.getContent();
    }

    public void editImage(CommunitySubjectImageEditorDTO communitySubjectImageEditorDTO) {
        this.title = communitySubjectImageEditorDTO.getTitle();
        this.content = communitySubjectImageEditorDTO.getContent();
        this.imageUrl = communitySubjectImageEditorDTO.getImageUrl();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }

    public void increaseCommentCount() {
        this.commentCount++;
    }

    public void decreaseCommentCount() {
        if (this.commentCount > 0) {
            this.commentCount--;
        }
    }
}
