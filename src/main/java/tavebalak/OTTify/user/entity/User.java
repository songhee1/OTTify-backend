package tavebalak.OTTify.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tavebalak.OTTify.oauth.constant.Role;
import tavebalak.OTTify.oauth.constant.SocialType;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_table")
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String nickName;
    private String profilePhoto;
    private double averageRating;
    private int level;
    
//    수정한 부분
    // socialType
    @Enumerated
    private SocialType socialType;

    private String socialId;

    @Enumerated
    private Role role;

    @Builder
    public User(String email, String nickName, String profilePhoto, SocialType socialType, String socialId, Role role) {
        this.email = email;
        this.nickName = nickName;
        this.profilePhoto = profilePhoto;
        this.averageRating = 0;
        this.level = 1;
        this.socialType = socialType;
        this.socialId = socialId;
        this.role = role;
    }
}
