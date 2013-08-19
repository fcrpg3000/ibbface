/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) QApiParam.java 2013-08-18 15:39
 */

package com.ibbface.domain.generated.privilege;

import com.google.common.collect.ImmutableMap;
import com.mysema.query.sql.PrimaryKey;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.BooleanPath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.Map;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import static com.ibbface.domain.model.privilege.ApiParam.*;

/**
 * @author Fuchun
 * @since 1.0
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QApiParam extends com.mysema.query.sql.RelationalPathBase<QApiParam> {
    private static final long serialVersionUID = 1L;

    public static final String API_PARAM_TABLE_NAME = "ibb_api_param";
    public static final QApiParam qApiParam = new QApiParam(API_PARAM_TABLE_NAME);

    public final NumberPath<Integer> id = createNumber("ID", Integer.class);

    public final NumberPath<Integer> resourceId = createNumber("RESOURCE_ID", Integer.class);

    public final StringPath paramName = createString("PARAM_NAME");

    public final StringPath paramRule = createString("PARAM_RULE");

    public final StringPath paramType = createString("PARAM_TYPE");

    public final StringPath defaultValue = createString("DEFAULT_VALUE");

    public final StringPath description = createString("DESCRIPTION");

    public final StringPath since = createString("SINCE");

    public final NumberPath<Integer> sortOrder = createNumber("SORT_ORDER", Integer.class);

    public final BooleanPath required = createBoolean("IS_REQUIRED");

    public final BooleanPath deprecated = createBoolean("IS_DEPRECATED");

    public final PrimaryKey<QApiParam> primary = createPrimaryKey(id);

    public final Path<?>[] sequenceColumns = new Path<?>[]{
            id, resourceId, paramName, paramRule, paramType, defaultValue,
            description, since, sortOrder, required, deprecated
    };

    public final Path<?>[] columnsWithoutId = Arrays.copyOfRange(
            sequenceColumns, 1, sequenceColumns.length);

    public final Map<String, Path<?>> propPathMap = buildPropPathMap();

    public QApiParam(String variable) {
        super(QApiParam.class, forVariable(variable), "null", API_PARAM_TABLE_NAME);
    }

    @SuppressWarnings("all")
    public QApiParam(Path<? extends QApiParam> path) {
        super((Class) path.getType(), path.getMetadata(), "null", API_PARAM_TABLE_NAME);
    }

    public QApiParam(PathMetadata<?> metadata) {
        super(QApiParam.class, metadata, "null", API_PARAM_TABLE_NAME);
    }

    private Map<String, Path<?>> buildPropPathMap() {
        ImmutableMap.Builder<String, Path<?>> builder = ImmutableMap.builder();
        return builder.put(PROP_ID, id)
                .put(PROP_RESOURCE_ID, resourceId)
                .put(PROP_PARAM_NAME, paramName)
                .put(PROP_PARAM_RULE, paramRule)
                .put(PROP_PARAM_TYPE, paramType)
                .put(PROP_DEFAULT_VALUE, defaultValue)
                .put(PROP_DESCRIPTION, description)
                .put(PROP_SINCE, since)
                .put(PROP_SORT_ORDER, sortOrder)
                .put(PROP_IS_REQUIRED, required)
                .put(PROP_IS_DEPRECATED, deprecated)
                .build();

    }
}
