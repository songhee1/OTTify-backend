package tavebalak.OTTify.genre.entity;

import tavebalak.OTTify.user.entity.User;

import javax.persistence.*;

@Entity
public class UserGenre {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;
    private boolean isFirst;

}
