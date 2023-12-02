package tavebalak.OTTify.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String nickName;
    private String profilePhoto;
    private double averageRating;
    private int level;
    private int socialType;

}
