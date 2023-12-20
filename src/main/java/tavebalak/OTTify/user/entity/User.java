package tavebalak.OTTify.user.entity;

import javax.persistence.*;

@Entity
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private SocialType socialType;
    private String email;
    private String nickName;
    private String profilePhoto;
    private double averageRating;
    @Enumerated(EnumType.STRING)
    private UserGradeType grade;
    @Enumerated(EnumType.STRING)
    private UserRoleType role;


}
