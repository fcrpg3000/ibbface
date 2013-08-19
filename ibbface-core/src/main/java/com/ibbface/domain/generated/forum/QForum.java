package com.ibbface.domain.generated.forum;

import com.google.common.collect.ImmutableMap;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.Map;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import static com.ibbface.domain.model.forum.Forum.*;


/**
 * QForum is a Querydsl query type for QForum
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QForum extends com.mysema.query.sql.RelationalPathBase<QForum> {

    private static final long serialVersionUID = -282637780;

    public static final String FORUM_TABLE_NAME = "IBB_FORUM";
    public static final QForum Forum = new QForum(FORUM_TABLE_NAME);

    public final DateTimePath<java.sql.Timestamp> createdTime = createDateTime(
            "CREATED_TIME", java.sql.Timestamp.class);

    public final NumberPath<Integer> id = createNumber("ID", Integer.class);

    public final StringPath idAlias = createString("ID_ALIAS");

    public final DateTimePath<java.sql.Timestamp> lastModifiedTime = createDateTime(
            "LAST_MODIFIED_TIME", java.sql.Timestamp.class);

    public final NumberPath<Integer> sortOrder = createNumber("SORT_ORDER", Integer.class);

    public final NumberPath<Short> status = createNumber("STATUS", Short.class);

    public final StringPath summary = createString("SUMMARY");

    public final StringPath title = createString("TITLE");

    public final NumberPath<Integer> options = createNumber("OPTIONS", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<QForum> primary = createPrimaryKey(id);

    public final Path<?>[] sequenceColumns = new Path<?>[]{
            id, idAlias, title, summary, sortOrder, status, options, createdTime, lastModifiedTime
    };

    public final Path<?>[] columnsWithoutId = Arrays.copyOfRange(
            sequenceColumns, 1, sequenceColumns.length);

    public final Map<String, Path<?>> propPathMap = buildPropPathMap();

    public QForum(String variable) {
        super(QForum.class, forVariable(variable), "null", FORUM_TABLE_NAME);
    }

    @SuppressWarnings("all")
    public QForum(Path<? extends QForum> path) {
        super((Class) path.getType(), path.getMetadata(), "null", FORUM_TABLE_NAME);
    }

    public QForum(PathMetadata<?> metadata) {
        super(QForum.class, metadata, "null", FORUM_TABLE_NAME);
    }

    private Map<String, Path<?>> buildPropPathMap() {
        ImmutableMap.Builder<String, Path<?>> builder = ImmutableMap.builder();
        return builder.put(PROP_ID, id)
                .put(PROP_ID_ALIAS, idAlias)
                .put(PROP_TITLE, title)
                .put(PROP_SUMMARY, summary)
                .put(PROP_SORT_ORDER, sortOrder)
                .put(PROP_STATUS, status)
                .put(PROP_OPTIONS, options)
                .put(PROP_CREATED_TIME, createdTime)
                .put(PROP_LAST_MODIFIED_TIME, lastModifiedTime)
                .build();
    }
}

