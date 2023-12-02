package tavebalak.OTTify.review.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Review {
    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;
    private String content;
    private double rating;
    private String genre;
}
