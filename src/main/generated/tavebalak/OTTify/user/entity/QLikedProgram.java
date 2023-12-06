package tavebalak.OTTify.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikedProgram is a Querydsl query type for LikedProgram
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikedProgram extends EntityPathBase<LikedProgram> {

    private static final long serialVersionUID = 403327917L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikedProgram likedProgram = new QLikedProgram("likedProgram");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final tavebalak.OTTify.program.entity.QProgram program;

    public final QUser user;

    public QLikedProgram(String variable) {
        this(LikedProgram.class, forVariable(variable), INITS);
    }

    public QLikedProgram(Path<? extends LikedProgram> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikedProgram(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikedProgram(PathMetadata metadata, PathInits inits) {
        this(LikedProgram.class, metadata, inits);
    }

    public QLikedProgram(Class<? extends LikedProgram> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.program = inits.isInitialized("program") ? new tavebalak.OTTify.program.entity.QProgram(forProperty("program")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

