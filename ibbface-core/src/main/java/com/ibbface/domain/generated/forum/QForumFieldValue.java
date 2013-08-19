package com.ibbface.domain.generated.forum;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QForumFieldValue is a Querydsl query type for QForumFieldValue
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QForumFieldValue extends com.mysema.query.sql.RelationalPathBase<QForumFieldValue> {

    private static final long serialVersionUID = -1240601629;

    public static final QForumFieldValue ForumFieldValue = new QForumFieldValue("ibb_forum_field_value");

    public final NumberPath<Long> fieldId = createNumber("field_id", Long.class);

    public final StringPath fieldName = createString("field_name");

    public final StringPath fieldValue = createString("field_value");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDefault = createBoolean("is_default");

    public final NumberPath<Long> parentId = createNumber("parent_id", Long.class);

    public final NumberPath<Integer> sortOrder = createNumber("sort_order", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<QForumFieldValue> primary = createPrimaryKey(id);

    public QForumFieldValue(String variable) {
        super(QForumFieldValue.class, forVariable(variable), "null", "ibb_forum_field_value");
    }

    @SuppressWarnings("all")
    public QForumFieldValue(Path<? extends QForumFieldValue> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "ibb_forum_field_value");
    }

    public QForumFieldValue(PathMetadata<?> metadata) {
        super(QForumFieldValue.class, metadata, "null", "ibb_forum_field_value");
    }

}

