package tavebalak.OTTify.program.entity;

import javax.persistence.*;

@Entity
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
