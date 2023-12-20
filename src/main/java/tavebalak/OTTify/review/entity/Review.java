package tavebalak.OTTify.review.entity;

import tavebalak.OTTify.common.entity.BaseEntity;
import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.user.entity.User;

import javax.persistence.*;

@Entity
public class Review extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program__id")
    private Program program;

    private String content;
    private double rating;
    private String genre;
}
