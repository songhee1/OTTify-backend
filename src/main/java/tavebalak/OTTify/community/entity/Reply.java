package tavebalak.OTTify.community.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Reply {
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

    @OneToMany(mappedBy = "parent")
    private List<Reply> child = new ArrayList<>();
}
