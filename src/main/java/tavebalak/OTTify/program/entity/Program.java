package tavebalak.OTTify.program.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Program {
    @Id
    @Column(name = "program_id")
    @GeneratedValue
    private Long id;
    private String title;
    private String posterPath;
    private double averageRating;
    private int reviewCount;
    private Long tmDbProgramId;
    @Enumerated(EnumType.STRING)
    private ProgramType type;

    @Builder
    Program(Long id, String title, String posterPath){
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
    }

}
