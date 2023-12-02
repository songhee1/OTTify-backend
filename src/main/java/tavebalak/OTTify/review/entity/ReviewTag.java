package tavebalak.OTTify.review.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class ReviewTag {
    @Id @GeneratedValue
    @Column(name = "review_tag_id")
    private Long id;
    private String name;
}
