package tavebalak.OTTify.community.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tavebalak.OTTify.common.BaseEntity;
import tavebalak.OTTify.community.dto.CommunitySubjectEditDTO;
import tavebalak.OTTify.community.dto.CommunitySubjectEditorDTO;
import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.user.entity.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Community extends BaseEntity {
    @Id @GeneratedValue
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

    @OneToMany(mappedBy = "community")
    private List<Reply> replyList = new ArrayList<>();

    @Builder
    public Community(String title, String content, Program program){
        this.title = title;
        this.content = content;
        this.program = program;
    }

    public CommunitySubjectEditorDTO.CommunitySubjectEditorDTOBuilder toEditor(){
        return CommunitySubjectEditorDTO.builder()
                .title(title)
                .content(content)
                .program(program);
    }

    public void edit(CommunitySubjectEditorDTO communitySubjectEditorDTO){
        this.title = communitySubjectEditorDTO.getTitle();
        this.content = communitySubjectEditorDTO.getContent();
        this.program = communitySubjectEditorDTO.getProgram();
    }
}
