package tavebalak.OTTify.program.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Ott {
    @Id @GeneratedValue
    @Column(name = "ott_id")
    private Long id;
    private String name;
    private String logoPath;
}
