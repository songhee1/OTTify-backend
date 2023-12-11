package tavebalak.OTTify.review.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tavebalak.OTTify.common.BaseEntity;
import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.user.entity.User;

import javax.persistence.*;

@Entity
@Getter @NoArgsConstructor
public class Review extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;
    private String content;
    private double rating;
    private String genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;

    @Builder
    public Review(String content, double rating, String genre, User user, Program program) {
        this.content = content;
        this.rating = rating;
        this.genre = genre;
        this.user = user;
        this.program = program;
    }
}
