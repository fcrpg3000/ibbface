package com.ibbface.domain.generated.user;

import com.google.common.collect.ImmutableMap;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.BooleanPath;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import javax.annotation.Generated;
import java.util.Map;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import static com.ibbface.domain.model.user.BannedUser.*;


/**
 * QBannedUser is a Querydsl query type for QBannedUser
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QBannedUser extends com.mysema.query.sql.RelationalPathBase<QBannedUser> {

    private static final long serialVersionUID = -63254050;

    public static final String BANNED_USER_TABLE = "ibb_banned_user";

    public static final QBannedUser BannedUser = new QBannedUser(BANNED_USER_TABLE);

    public final StringPath bannedCause = createString("BANNED_CAUSE");

    public final DateTimePath<java.sql.Timestamp> bannedTime = createDateTime(
            "BANNED_TIME", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> createdTime = createDateTime(
            "CREATED_TIME", java.sql.Timestamp.class);

    public final NumberPath<Long> id = createNumber("ID", Long.class);

    public final BooleanPath isPermanent = createBoolean("IS_PERMANENT");

    public final DateTimePath<java.sql.Timestamp> lastModifiedTime = createDateTime(
            "LAST_MODIFIED_TIME", java.sql.Timestamp.class);

    public final NumberPath<Integer> operatorId = createNumber("OPERATOR_ID", Integer.class);

    public final StringPath operatorName = createString("OPERATOR_NAME");

    public final DateTimePath<java.sql.Timestamp> unbannedTime = createDateTime(
            "UNBANNED_TIME", java.sql.Timestamp.class);

    public final NumberPath<Long> userId = createNumber("USER_ID", Long.class);

    public final StringPath userName = createString("USER_NAME");

    public final com.mysema.query.sql.PrimaryKey<QBannedUser> primary = createPrimaryKey(id);

    public final Map<String, Path<?>> propPathMap = buildPropPathMap();

    public final Path<?>[] allColumns = new Path<?>[]{
            id, userId, userName, operatorId, operatorName, bannedCause,
            isPermanent, bannedTime, unbannedTime, createdTime, lastModifiedTime
    };

    public QBannedUser(String variable) {
        super(QBannedUser.class, forVariable(variable), "null", BANNED_USER_TABLE);
    }

    @SuppressWarnings("all")
    public QBannedUser(Path<? extends QBannedUser> path) {
        super((Class) path.getType(), path.getMetadata(), "null", BANNED_USER_TABLE);
    }

    public QBannedUser(PathMetadata<?> metadata) {
        super(QBannedUser.class, metadata, "null", BANNED_USER_TABLE);
    }

    private Map<String, Path<?>> buildPropPathMap() {
        ImmutableMap.Builder<String, Path<?>> builder = ImmutableMap.builder();
        return builder.put(PROP_ID, id)
                .put(PROP_USER_ID, userId)
                .put(PROP_USER_NAME, userName)
                .put(PROP_OPERATOR_ID, operatorId)
                .put(PROP_OPERATOR_NAME, operatorName)
                .put(PROP_IS_PERMANENT, isPermanent)
                .put(PROP_BANNED_CAUSE, bannedCause)
                .put(PROP_BANNED_TIME, bannedTime)
                .put(PROP_UNBANNED_TIME, unbannedTime)
                .put(PROP_CREATED_TIME, createdTime)
                .put(PROP_LAST_MODIFIED_TIME, lastModifiedTime)
                .build();
    }
}

