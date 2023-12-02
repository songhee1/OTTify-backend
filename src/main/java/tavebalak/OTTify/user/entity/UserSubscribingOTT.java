package tavebalak.OTTify.user.entity;

import tavebalak.OTTify.program.entity.Ott;

import javax.persistence.*;
@Entity
public class UserSubscribingOTT {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ott_id")
    private Ott ott;
}
