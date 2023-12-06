package tavebalak.OTTify.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserSubscribingOTT is a Querydsl query type for UserSubscribingOTT
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserSubscribingOTT extends EntityPathBase<UserSubscribingOTT> {

    private static final long serialVersionUID = 621869033L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserSubscribingOTT userSubscribingOTT = new QUserSubscribingOTT("userSubscribingOTT");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final tavebalak.OTTify.program.entity.QOtt ott;

    public final QUser user;

    public QUserSubscribingOTT(String variable) {
        this(UserSubscribingOTT.class, forVariable(variable), INITS);
    }

    public QUserSubscribingOTT(Path<? extends UserSubscribingOTT> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserSubscribingOTT(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserSubscribingOTT(PathMetadata metadata, PathInits inits) {
        this(UserSubscribingOTT.class, metadata, inits);
    }

    public QUserSubscribingOTT(Class<? extends UserSubscribingOTT> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ott = inits.isInitialized("ott") ? new tavebalak.OTTify.program.entity.QOtt(forProperty("ott")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

