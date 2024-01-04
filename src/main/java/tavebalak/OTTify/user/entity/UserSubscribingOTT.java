package tavebalak.OTTify.user.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tavebalak.OTTify.program.entity.Ott;

import javax.persistence.*;
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSubscribingOTT {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ott_id")
    private Ott ott;

    public static UserSubscribingOTT create(User user, Ott ott) {
        return UserSubscribingOTT.builder()
                .user(user)
                .ott(ott)
                .build();
    }

    @Builder
    public UserSubscribingOTT(Long id, User user, Ott ott) {
        this.id = id;
        this.user = user;
        this.ott = ott;
    }
}
