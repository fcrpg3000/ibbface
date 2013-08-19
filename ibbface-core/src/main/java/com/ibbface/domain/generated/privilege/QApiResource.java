/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) QApiResource.java 2013-08-18 15:19
 */

package com.ibbface.domain.generated.privilege;

import com.google.common.collect.ImmutableMap;
import com.mysema.query.sql.PrimaryKey;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.BooleanPath;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import javax.annotation.Generated;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Map;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import static com.ibbface.domain.model.privilege.ApiResource.*;

/**
 * QApiResource is a Querydsl query type for QApiResource.
 *
 * @author Fuchun
 * @since 1.0
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QApiResource extends com.mysema.query.sql.RelationalPathBase<QApiResource> {
    private static final long serialVersionUID = 1L;

    public static final String API_RESOURCE_TABLE_NAME = "ibb_api_resource";
    public static final QApiResource qApiResource = new QApiResource(API_RESOURCE_TABLE_NAME);

    public final NumberPath<Integer> id = createNumber("ID", Integer.class);

    public final NumberPath<Integer> parentId = createNumber("PARENT_ID", Integer.class);

    public final StringPath basePath = createString("BASE_PATH");

    public final StringPath description = createString("DESCRIPTION");

    public final StringPath version = createString("VERSION");

    public final NumberPath<Short> gradeCode = createNumber("GRADE_CODE", Short.class);

    public final StringPath httpMethod = createString("HTTP_METHOD");

    public final StringPath dataType = createString("DATA_TYPE");

    public final StringPath others = createString("OTHERS");

    public final BooleanPath requireLogin = createBoolean("REQUIRE_LOGIN");

    public final BooleanPath isEnabled = createBoolean("IS_ENABLED");

    public final DateTimePath<Timestamp> createdTime = createDateTime("CREATED_TIME", Timestamp.class);

    public final PrimaryKey<QApiResource> primary = createPrimaryKey(id);

    public final Path<?>[] sequenceColumns = new Path<?>[]{
            id, parentId, basePath, description, version, gradeCode,
            httpMethod, dataType, others, requireLogin, isEnabled, createdTime
    };

    public final Path<?>[] columnsWithoutId = Arrays.copyOfRange(
            sequenceColumns, 1, sequenceColumns.length);

    public final Map<String, Path<?>> propPathMap = buildPropPathMap();

    public QApiResource(String variable) {
        super(QApiResource.class, forVariable(variable), "null", API_RESOURCE_TABLE_NAME);
    }

    @SuppressWarnings("all")
    public QApiResource(Path<? extends QApiResource> path) {
        super((Class) path.getType(), path.getMetadata(), "null", API_RESOURCE_TABLE_NAME);
    }

    public QApiResource(PathMetadata<?> metadata) {
        super(QApiResource.class, metadata, "null", API_RESOURCE_TABLE_NAME);
    }

    private Map<String, Path<?>> buildPropPathMap() {
        ImmutableMap.Builder<String, Path<?>> builder = ImmutableMap.builder();
        return builder.put(PROP_ID, id)
                .put(PROP_PARENT_ID, parentId)
                .put(PROP_BASE_PATH, basePath)
                .put(PROP_DESCRIPTION, description)
                .put(PROP_VERSION, version)
                .put(PROP_GRADE_CODE, gradeCode)
                .put(PROP_HTTP_METHOD, httpMethod)
                .put(PROP_DATA_TYPE, dataType)
                .put(PROP_OTHERS, others)
                .put(PROP_REQUIRE_LOGIN, requireLogin)
                .put(PROP_IS_ENABLED, isEnabled)
                .put(PROP_CREATED_TIME, createdTime)
                .build();
    }
}
