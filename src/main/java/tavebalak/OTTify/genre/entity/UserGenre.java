package tavebalak.OTTify.genre.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tavebalak.OTTify.user.entity.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserGenre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_genre_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    private boolean isFirst;

    @Builder
    public UserGenre(User user, Genre genre, boolean isFirst) {
        this.user = user;
        this.genre = genre;
        this.isFirst = isFirst;
    }

    public static UserGenre create(User user, Genre genre, boolean isFirst) {
        return UserGenre.builder()
            .user(user)
            .genre(genre)
            .isFirst(isFirst)
            .build();
    }

    public void changeGenre(Genre genre) {
        this.genre = genre;
    }
}
