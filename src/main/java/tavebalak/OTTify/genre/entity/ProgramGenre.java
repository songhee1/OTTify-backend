package tavebalak.OTTify.genre.entity;

import tavebalak.OTTify.program.entity.Program;

import javax.persistence.*;

@Entity
public class ProgramGenre {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;
}
