package com.ibbface.domain.generated.forum;

import com.google.common.collect.ImmutableMap;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.BooleanPath;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.Map;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import static com.ibbface.domain.model.forum.ForumThread.*;


/**
 * QForumThread is a Querydsl query type for QForumThread
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QForumThread extends com.mysema.query.sql.RelationalPathBase<QForumThread> {

    private static final long serialVersionUID = 222491231;

    public static final String FORUM_THREAD_TABLE_NAME = "ibb_thread";
    public static final QForumThread Thread = new QForumThread(FORUM_THREAD_TABLE_NAME);

    public final NumberPath<Long> id = createNumber("ID", Long.class);

    public final StringPath idAlias = createString("ID_ALIAS");

    public final NumberPath<Long> userId = createNumber("USER_ID", Long.class);

    public final NumberPath<Integer> forumId = createNumber("FORUM_ID", Integer.class);

    public final NumberPath<Short> typeId = createNumber("TYPE_ID", Short.class);

    public final StringPath title = createString("TITLE");

    public final StringPath content = createString("CONTENT");

    public final StringPath tags = createString("TAGS");

    public final StringPath sourceUrl = createString("SOURCE_URL");

    public final NumberPath<Integer> viewCount = createNumber("VIEW_COUNT", Integer.class);

    public final NumberPath<Integer> replyCount = createNumber("REPLY_COUNT", Integer.class);

    public final NumberPath<Long> lastPostId = createNumber("LAST_POST_ID", Long.class);

    public final NumberPath<Long> lastPostUserId = createNumber("LAST_POST_USER_ID", Long.class);

    public final DateTimePath<java.sql.Timestamp> lastPostTime = createDateTime(
            "LAST_POST_TIME", java.sql.Timestamp.class);

    public final BooleanPath isTop = createBoolean("IS_TOP");

    public final BooleanPath isGood = createBoolean("IS_GOOD");

    public final NumberPath<Short> clientCode = createNumber("CLIENT_CODE", Short.class);

    public final StringPath clientIp = createString("CLIENT_IP");

    public final DateTimePath<java.sql.Timestamp> createdTime = createDateTime(
            "CREATED_TIME", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> lastModifiedTime = createDateTime(
            "LAST_MODIFIED_TIME", java.sql.Timestamp.class);

    public final NumberPath<Integer> options = createNumber("OPTIONS", Integer.class);

    public final NumberPath<Short> status = createNumber("STATUS", Short.class);

    public final Path<?>[] sequenceColumns = new Path<?>[]{
            id, idAlias, userId, forumId, typeId, title, content, tags,
            sourceUrl, viewCount, replyCount, lastPostId, lastPostUserId,
            lastPostTime, isTop, isGood, clientCode, clientIp, createdTime,
            lastModifiedTime, options, status
    };

    public final Path<?>[] columnsWithoutId = Arrays.copyOfRange(
            sequenceColumns, 1, sequenceColumns.length);

    public final Map<String, Path<?>> propPathMap = buildPropPathMap();

    public final com.mysema.query.sql.PrimaryKey<QForumThread> primary = createPrimaryKey(id);

    public QForumThread(String variable) {
        super(QForumThread.class, forVariable(variable), "null", FORUM_THREAD_TABLE_NAME);
    }

    @SuppressWarnings("all")
    public QForumThread(Path<? extends QForumThread> path) {
        super((Class) path.getType(), path.getMetadata(), "null", FORUM_THREAD_TABLE_NAME);
    }

    public QForumThread(PathMetadata<?> metadata) {
        super(QForumThread.class, metadata, "null", FORUM_THREAD_TABLE_NAME);
    }

    private Map<String, Path<?>> buildPropPathMap() {
        ImmutableMap.Builder<String, Path<?>> builder = ImmutableMap.builder();
        return builder.put(PROP_ID, id)
                .put(PROP_ID_ALIAS, idAlias)
                .put(PROP_USER_ID, userId)
                .put(PROP_FORUM_ID, forumId)
                .put(PROP_TYPE_ID, typeId)
                .put(PROP_TITLE, title)
                .put(PROP_CONTENT, content)
                .put(PROP_TAGS, tags)
                .put(PROP_SOURCE_URL, sourceUrl)
                .put(PROP_VIEW_COUNT, viewCount)
                .put(PROP_REPLY_COUNT, replyCount)
                .put(PROP_LAST_POST_ID, lastPostId)
                .put(PROP_LAST_POST_USER_ID, lastPostUserId)
                .put(PROP_LAST_POST_TIME, lastPostTime)
                .put(PROP_IS_TOP, isTop)
                .put(PROP_IS_GOOD, isGood)
                .put(PROP_CLIENT_CODE, clientCode)
                .put(PROP_CLIENT_IP, clientIp)
                .put(PROP_CREATED_TIME, createdTime)
                .put(PROP_LAST_MODIFIED_TIME, lastModifiedTime)
                .put(PROP_OPTIONS, options)
                .put(PROP_STATUS, status)
                .build();
    }
}

