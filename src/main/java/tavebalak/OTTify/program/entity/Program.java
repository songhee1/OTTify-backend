package tavebalak.OTTify.program.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class Program {
    @Id @GeneratedValue
    @Column(name = "program_id")
    private Long id;
    private String title;
    private String posterPath;
    private double averageRating;
    private int reviewCount; //메인페이지에서 보기위함
}
