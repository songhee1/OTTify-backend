package tavebalak.OTTify.user.entity;

import tavebalak.OTTify.review.entity.Review;

import javax.persistence.*;

@Entity
public class LikedReview {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
