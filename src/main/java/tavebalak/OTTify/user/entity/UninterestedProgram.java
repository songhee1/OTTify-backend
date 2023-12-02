package tavebalak.OTTify.user.entity;

import tavebalak.OTTify.program.entity.Program;

import javax.persistence.*;
@Entity
public class UninterestedProgram {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;
}
