package com.ibbface.domain.generated;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.BooleanPath;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import javax.annotation.Generated;

import static com.mysema.query.types.PathMetadataFactory.forVariable;


/**
 * QApp is a Querydsl query type for QApp
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QApp extends com.mysema.query.sql.RelationalPathBase<QApp> {

    private static final long serialVersionUID = -1792475668;

    public static final QApp App = new QApp("ibb_app");

    public final NumberPath<Integer> appId = createNumber("app_id", Integer.class);

    public final StringPath appKey = createString("app_key");

    public final StringPath appName = createString("app_name");

    public final NumberPath<Integer> appOs = createNumber("app_os", Integer.class);

    public final StringPath appSecret = createString("app_secret");

    public final StringPath appUrl = createString("app_url");

    public final NumberPath<Integer> baseAppOs = createNumber("base_app_os", Integer.class);

    public final NumberPath<Integer> cateId = createNumber("cate_id", Integer.class);

    public final DateTimePath<java.sql.Timestamp> createdTime = createDateTime("created_time", java.sql.Timestamp.class);

    public final StringPath description = createString("description");

    public final BooleanPath isBindDomain = createBoolean("is_bind_domain");

    public final DateTimePath<java.sql.Timestamp> lastModifiedTime = createDateTime("last_modified_time", java.sql.Timestamp.class);

    public final StringPath summary = createString("summary");

    public final NumberPath<Integer> tagId1 = createNumber("tag_id1", Integer.class);

    public final NumberPath<Integer> tagId2 = createNumber("tag_id2", Integer.class);

    public final NumberPath<Integer> tagId3 = createNumber("tag_id3", Integer.class);

    public final NumberPath<Long> userId = createNumber("user_id", Long.class);

    public final StringPath userName = createString("user_name");

    public final com.mysema.query.sql.PrimaryKey<QApp> primary = createPrimaryKey(appId);

    public QApp(String variable) {
        super(QApp.class, forVariable(variable), "null", "ibb_app");
    }

    @SuppressWarnings("all")
    public QApp(Path<? extends QApp> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "ibb_app");
    }

    public QApp(PathMetadata<?> metadata) {
        super(QApp.class, metadata, "null", "ibb_app");
    }

}

