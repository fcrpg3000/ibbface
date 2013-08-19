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
 * QDeveloper is a Querydsl query type for QDeveloper
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDeveloper extends com.mysema.query.sql.RelationalPathBase<QDeveloper> {

    private static final long serialVersionUID = -1711593643;

    public static final QDeveloper Developer = new QDeveloper("ibb_developer");

    public final NumberPath<Integer> cityId = createNumber("city_id", Integer.class);

    public final DateTimePath<java.sql.Timestamp> createdTime = createDateTime("created_time", java.sql.Timestamp.class);

    public final StringPath devIm = createString("dev_im");

    public final NumberPath<Byte> devImType = createNumber("dev_im_type", Byte.class);

    public final StringPath devName = createString("dev_name");

    public final StringPath devSite = createString("dev_site");

    public final NumberPath<Byte> devType = createNumber("dev_type", Byte.class);

    public final StringPath email = createString("email");

    public final BooleanPath isTrusted = createBoolean("is_trusted");

    public final DateTimePath<java.sql.Timestamp> lastModifiedTime = createDateTime("last_modified_time", java.sql.Timestamp.class);

    public final StringPath phone = createString("phone");

    public final NumberPath<Integer> provinceId = createNumber("province_id", Integer.class);

    public final NumberPath<Long> userId = createNumber("user_id", Long.class);

    public final StringPath userName = createString("user_name");

    public final com.mysema.query.sql.PrimaryKey<QDeveloper> primary = createPrimaryKey(userId);

    public QDeveloper(String variable) {
        super(QDeveloper.class, forVariable(variable), "null", "ibb_developer");
    }

    @SuppressWarnings("all")
    public QDeveloper(Path<? extends QDeveloper> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "ibb_developer");
    }

    public QDeveloper(PathMetadata<?> metadata) {
        super(QDeveloper.class, metadata, "null", "ibb_developer");
    }

}

