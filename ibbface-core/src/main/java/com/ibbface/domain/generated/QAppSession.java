package com.ibbface.domain.generated;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import javax.annotation.Generated;

import static com.mysema.query.types.PathMetadataFactory.forVariable;


/**
 * QAppSession is a Querydsl query type for QAppSession
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QAppSession extends com.mysema.query.sql.RelationalPathBase<QAppSession> {

    private static final long serialVersionUID = -196158710;

    public static final QAppSession AppSession = new QAppSession("ibb_app_session");

    public final StringPath accessToken = createString("access_token");

    public final StringPath appAuthCode = createString("app_auth_code");

    public final NumberPath<Integer> appId = createNumber("app_id", Integer.class);

    public final StringPath appKey = createString("app_key");

    public final StringPath appName = createString("app_name");

    public final StringPath appSecret = createString("app_secret");

    public final DateTimePath<java.sql.Timestamp> createdTime = createDateTime("created_time", java.sql.Timestamp.class);

    public final NumberPath<Long> lastAccessTime = createNumber("last_access_time", Long.class);

    public final DateTimePath<java.sql.Timestamp> lastModifiedTime = createDateTime("last_modified_time", java.sql.Timestamp.class);

    public final com.mysema.query.sql.PrimaryKey<QAppSession> primary = createPrimaryKey(appId);

    public QAppSession(String variable) {
        super(QAppSession.class, forVariable(variable), "null", "ibb_app_session");
    }

    @SuppressWarnings("all")
    public QAppSession(Path<? extends QAppSession> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "ibb_app_session");
    }

    public QAppSession(PathMetadata<?> metadata) {
        super(QAppSession.class, metadata, "null", "ibb_app_session");
    }

}

