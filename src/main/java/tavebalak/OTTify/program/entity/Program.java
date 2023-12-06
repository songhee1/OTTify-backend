package tavebalak.OTTify.program.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Program {
    @Id @GeneratedValue
    @Column(name = "program_id")
    private Long id;
    private String title;
    private String posterPath;
    private double averageRating;
    private int reviewCount; //메인페이지에서 보기위함

    @Builder
    Program(String title, String posterPath){
        this.title = title;
        this.posterPath = posterPath;
    }
}
