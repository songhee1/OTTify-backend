package tavebalak.OTTify.program.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Program {
    @Id @GeneratedValue
    @Column(name = "program_id")
    private Long id;
    private String title;
    private String posterPath;
    private double averageRating;
    private int reviewCount;
    private Long tmdb_program_id;
    @Enumerated(EnumType.STRING)
    private ProgramType type;

}
