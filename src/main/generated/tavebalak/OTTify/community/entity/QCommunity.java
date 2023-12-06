package tavebalak.OTTify.community.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommunity is a Querydsl query type for Community
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommunity extends EntityPathBase<Community> {

    private static final long serialVersionUID = 1374317393L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommunity community = new QCommunity("community");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final tavebalak.OTTify.program.entity.QProgram program;

    public final ListPath<Reply, QReply> replyList = this.<Reply, QReply>createList("replyList", Reply.class, QReply.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public final tavebalak.OTTify.user.entity.QUser user;

    public QCommunity(String variable) {
        this(Community.class, forVariable(variable), INITS);
    }

    public QCommunity(Path<? extends Community> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommunity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommunity(PathMetadata metadata, PathInits inits) {
        this(Community.class, metadata, inits);
    }

    public QCommunity(Class<? extends Community> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.program = inits.isInitialized("program") ? new tavebalak.OTTify.program.entity.QProgram(forProperty("program")) : null;
        this.user = inits.isInitialized("user") ? new tavebalak.OTTify.user.entity.QUser(forProperty("user")) : null;
    }

}

