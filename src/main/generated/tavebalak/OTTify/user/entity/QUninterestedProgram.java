package tavebalak.OTTify.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUninterestedProgram is a Querydsl query type for UninterestedProgram
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUninterestedProgram extends EntityPathBase<UninterestedProgram> {

    private static final long serialVersionUID = -388207316L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUninterestedProgram uninterestedProgram = new QUninterestedProgram("uninterestedProgram");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final tavebalak.OTTify.program.entity.QProgram program;

    public final QUser user;

    public QUninterestedProgram(String variable) {
        this(UninterestedProgram.class, forVariable(variable), INITS);
    }

    public QUninterestedProgram(Path<? extends UninterestedProgram> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUninterestedProgram(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUninterestedProgram(PathMetadata metadata, PathInits inits) {
        this(UninterestedProgram.class, metadata, inits);
    }

    public QUninterestedProgram(Class<? extends UninterestedProgram> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.program = inits.isInitialized("program") ? new tavebalak.OTTify.program.entity.QProgram(forProperty("program")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

