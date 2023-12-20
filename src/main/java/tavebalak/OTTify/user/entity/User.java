package tavebalak.OTTify.user.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "\"User\"")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String email;
    private String nickName;
    private String profilePhoto;
    private double averageRating;

    @Enumerated(EnumType.STRING)
    private GradeType grade;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    private RoleType role;
}
