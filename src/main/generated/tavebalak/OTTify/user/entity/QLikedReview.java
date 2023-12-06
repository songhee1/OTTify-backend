package tavebalak.OTTify.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikedReview is a Querydsl query type for LikedReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikedReview extends EntityPathBase<LikedReview> {

    private static final long serialVersionUID = 1721041167L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikedReview likedReview = new QLikedReview("likedReview");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final tavebalak.OTTify.review.entity.QReview review;

    public final QUser user;

    public QLikedReview(String variable) {
        this(LikedReview.class, forVariable(variable), INITS);
    }

    public QLikedReview(Path<? extends LikedReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikedReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikedReview(PathMetadata metadata, PathInits inits) {
        this(LikedReview.class, metadata, inits);
    }

    public QLikedReview(Class<? extends LikedReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.review = inits.isInitialized("review") ? new tavebalak.OTTify.review.entity.QReview(forProperty("review")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

