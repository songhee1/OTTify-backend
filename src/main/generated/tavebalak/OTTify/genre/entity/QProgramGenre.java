package tavebalak.OTTify.genre.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProgramGenre is a Querydsl query type for ProgramGenre
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProgramGenre extends EntityPathBase<ProgramGenre> {

    private static final long serialVersionUID = -1219503471L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProgramGenre programGenre = new QProgramGenre("programGenre");

    public final QGenre genre;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final tavebalak.OTTify.program.entity.QProgram program;

    public QProgramGenre(String variable) {
        this(ProgramGenre.class, forVariable(variable), INITS);
    }

    public QProgramGenre(Path<? extends ProgramGenre> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProgramGenre(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProgramGenre(PathMetadata metadata, PathInits inits) {
        this(ProgramGenre.class, metadata, inits);
    }

    public QProgramGenre(Class<? extends ProgramGenre> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.genre = inits.isInitialized("genre") ? new QGenre(forProperty("genre")) : null;
        this.program = inits.isInitialized("program") ? new tavebalak.OTTify.program.entity.QProgram(forProperty("program")) : null;
    }

}

