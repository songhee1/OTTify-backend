package tavebalak.OTTify.program.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;
import tavebalak.OTTify.genre.entity.Genre;
import tavebalak.OTTify.genre.entity.ProgramGenre;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    private String createdYear;

    @Builder
    public Program(String title,String posterPath,Long tmDbProgramId,ProgramType type,String createdYear){
        this.title=title;
        this.posterPath=posterPath;
        this.tmDbProgramId=tmDbProgramId;
        this.type=type;
        this.createdYear=createdYear;
        this.averageRating = 0;
        this.reviewCount = 0;
    }


    @Builder(builderMethodName = "testBuilder")
    public Program(Long id, String title, String posterPath) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
    }










    @OneToMany(mappedBy = "program",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ProgramGenre> programGenreList=new ArrayList<>();



    public void addGenre(Genre genre){
        ProgramGenre programGenre=ProgramGenre.builder().genre(genre).program(this).build();
        programGenreList.add(programGenre);
    }

}
