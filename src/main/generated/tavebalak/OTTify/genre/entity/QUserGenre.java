package tavebalak.OTTify.genre.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserGenre is a Querydsl query type for UserGenre
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserGenre extends EntityPathBase<UserGenre> {

    private static final long serialVersionUID = -1117797242L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserGenre userGenre = new QUserGenre("userGenre");

    public final QGenre genre;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isFirst = createBoolean("isFirst");

    public final tavebalak.OTTify.user.entity.QUser user;

    public QUserGenre(String variable) {
        this(UserGenre.class, forVariable(variable), INITS);
    }

    public QUserGenre(Path<? extends UserGenre> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserGenre(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserGenre(PathMetadata metadata, PathInits inits) {
        this(UserGenre.class, metadata, inits);
    }

    public QUserGenre(Class<? extends UserGenre> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.genre = inits.isInitialized("genre") ? new QGenre(forProperty("genre")) : null;
        this.user = inits.isInitialized("user") ? new tavebalak.OTTify.user.entity.QUser(forProperty("user")) : null;
    }

}

