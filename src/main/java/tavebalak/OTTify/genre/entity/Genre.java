package tavebalak.OTTify.genre.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Genre {
    @Id @GeneratedValue
    @Column(name = "genre_id")
    private Long id;
    private String name;

}
