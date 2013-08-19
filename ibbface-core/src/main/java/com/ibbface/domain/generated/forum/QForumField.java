package com.ibbface.domain.generated.forum;

import com.google.common.collect.ImmutableMap;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.BooleanPath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.Map;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import static com.ibbface.domain.model.forum.ForumField.*;


/**
 * QForumField is a Querydsl query type for QForumField
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QForumField extends com.mysema.query.sql.RelationalPathBase<QForumField> {

    private static final long serialVersionUID = 116908590;

    public static final String FORUM_FIELD_TABLE_NAME = "IBB_FORUM_FIELD";
    public static final QForumField ForumField = new QForumField(FORUM_FIELD_TABLE_NAME);

    public final StringPath fieldName = createString("FIELD_NAME");

    public final StringPath fieldRule = createString("FIELD_RULE");

    public final NumberPath<Short> fieldType = createNumber("FIELD_TYPE", Short.class);

    public final NumberPath<Integer> forumId = createNumber("FORUM_ID", Integer.class);

    public final NumberPath<Long> id = createNumber("ID", Long.class);

    public final NumberPath<Integer> options = createNumber("OPTIONS", Integer.class);

    public final BooleanPath required = createBoolean("REQUIRED");

    public final NumberPath<Integer> sortOrder = createNumber("SORT_ORDER", Integer.class);

    public final StringPath summary = createString("SUMMARY");

    public final com.mysema.query.sql.PrimaryKey<QForumField> primary = createPrimaryKey(id);

    public final Path<?>[] sequenceColumns = new Path<?>[]{
            id, forumId, fieldName, summary, fieldRule,
            fieldType, sortOrder, required, options
    };

    public final Path<?>[] columnsWithoutId = Arrays.copyOfRange(
            sequenceColumns, 1, sequenceColumns.length);

    public final Map<String, Path<?>> propPathMap = buildPropPathMap();

    public QForumField(String variable) {
        super(QForumField.class, forVariable(variable), "null", FORUM_FIELD_TABLE_NAME);
    }

    @SuppressWarnings("all")
    public QForumField(Path<? extends QForumField> path) {
        super((Class) path.getType(), path.getMetadata(), "null", FORUM_FIELD_TABLE_NAME);
    }

    public QForumField(PathMetadata<?> metadata) {
        super(QForumField.class, metadata, "null", FORUM_FIELD_TABLE_NAME);
    }

    private Map<String, Path<?>> buildPropPathMap() {
        ImmutableMap.Builder<String, Path<?>> builder = ImmutableMap.builder();
        return builder.put(PROP_ID, id)
                .put(PROP_FORUM_ID, forumId)
                .put(PROP_FIELD_NAME, fieldName)
                .put(PROP_SUMMARY, summary)
                .put(PROP_FIELD_RULE, fieldRule)
                .put(PROP_FIELD_TYPE, fieldType)
                .put(PROP_SORT_ORDER, sortOrder)
                .put(PROP_IS_REQUIRED, required)
                .put(PROP_OPTIONS, options)
                .build();
    }
}

