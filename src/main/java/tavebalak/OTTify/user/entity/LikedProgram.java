package tavebalak.OTTify.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tavebalak.OTTify.program.entity.Program;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class LikedProgram {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;

    @Builder
    public LikedProgram(User user, Program program) {
        this.user = user;
        this.program = program;
    }
}
