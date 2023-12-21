package tavebalak.OTTify.program.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ott {
    @Id @GeneratedValue
    @Column(name = "ott_id")
    private Long id;
    private String name;
    private String logoPath;
}
