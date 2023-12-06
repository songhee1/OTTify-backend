package tavebalak.OTTify.review.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReviewTag is a Querydsl query type for ReviewTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewTag extends EntityPathBase<ReviewTag> {

    private static final long serialVersionUID = -1246472481L;

    public static final QReviewTag reviewTag = new QReviewTag("reviewTag");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QReviewTag(String variable) {
        super(ReviewTag.class, forVariable(variable));
    }

    public QReviewTag(Path<? extends ReviewTag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReviewTag(PathMetadata metadata) {
        super(ReviewTag.class, metadata);
    }

}

