package tavebalak.OTTify.review.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReviewReviewTag is a Querydsl query type for ReviewReviewTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewReviewTag extends EntityPathBase<ReviewReviewTag> {

    private static final long serialVersionUID = 1419133095L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReviewReviewTag reviewReviewTag = new QReviewReviewTag("reviewReviewTag");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QReview review;

    public final QReviewTag reviewTag;

    public QReviewReviewTag(String variable) {
        this(ReviewReviewTag.class, forVariable(variable), INITS);
    }

    public QReviewReviewTag(Path<? extends ReviewReviewTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReviewReviewTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReviewReviewTag(PathMetadata metadata, PathInits inits) {
        this(ReviewReviewTag.class, metadata, inits);
    }

    public QReviewReviewTag(Class<? extends ReviewReviewTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.review = inits.isInitialized("review") ? new QReview(forProperty("review")) : null;
        this.reviewTag = inits.isInitialized("reviewTag") ? new QReviewTag(forProperty("reviewTag")) : null;
    }

}

