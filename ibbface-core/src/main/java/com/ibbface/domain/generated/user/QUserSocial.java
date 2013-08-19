/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) QUserSocial.java 2013-08-01 23:28
 */

package com.ibbface.domain.generated.user;

import com.google.common.collect.ImmutableMap;
import com.mysema.query.sql.PrimaryKey;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import javax.annotation.Generated;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Map;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import static com.ibbface.domain.model.user.UserSocial.*;

/**
 * QUserSocial is a Querydsl query type for UserSocial
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QUserSocial extends com.mysema.query.sql.RelationalPathBase<QUserSocial> {

    private static final long serialVersionUID = 1L;

    public static final String USER_SOCIAL_TABLE_NAME = "ibb_user_social";

    public static final QUserSocial qUserSocial = new QUserSocial(USER_SOCIAL_TABLE_NAME);

    public final NumberPath<Long> id = createNumber("ID", Long.class);

    public final NumberPath<Long> userId = createNumber("USER_ID", Long.class);

    public final StringPath openUid = createString("OPEN_UID");

    public final NumberPath<Short> openProviderCode = createNumber(
            "OPEN_PROVIDER_CODE", Short.class);

    public final StringPath accessToken = createString("ACCESS_TOKEN");

    public final NumberPath<Long> expiresIn = createNumber("EXPIRES_IN", Long.class);

    public final DateTimePath<Timestamp> createdTime = createDateTime(
            "CREATED_TIME", Timestamp.class);

    public final DateTimePath<Timestamp> lastModifiedTime = createDateTime(
            "LAST_MODIFIED_TIME", Timestamp.class);

    public final Path<?>[] sequenceColumns = new Path<?>[]{
            id, userId, openUid, openProviderCode, accessToken, expiresIn,
            createdTime, lastModifiedTime
    };

    public final Path<?>[] columnsWithoutId = Arrays.copyOfRange(
            sequenceColumns, 1, sequenceColumns.length);

    public final Map<String, Path<?>> propPathMap = buildPropPathMap();

    public final PrimaryKey<QUserSocial> primary = createPrimaryKey(id);

    public QUserSocial(String variable) {
        super(QUserSocial.class, forVariable(variable), "null", USER_SOCIAL_TABLE_NAME);
    }

    @SuppressWarnings("all")
    public QUserSocial(Path<? extends QUserSocial> path) {
        super((Class) path.getType(), path.getMetadata(), "null", USER_SOCIAL_TABLE_NAME);
    }

    public QUserSocial(PathMetadata<?> metadata) {
        super(QUserSocial.class, metadata, "null", USER_SOCIAL_TABLE_NAME);
    }

    private Map<String, Path<?>> buildPropPathMap() {
        ImmutableMap.Builder<String, Path<?>> builder = ImmutableMap.builder();
        return builder.put(PROP_ID, id)
                .put(PROP_USER_ID, userId)
                .put(PROP_OPEN_UID, openUid)
                .put(PROP_OPEN_PROVIDER_CODE, openProviderCode)
                .put(PROP_ACCESS_TOKEN, accessToken)
                .put(PROP_EXPIRES_IN, expiresIn)
                .put(PROP_CREATED_TIME, createdTime)
                .put(PROP_LAST_MODIFIED_TIME, lastModifiedTime)
                .build();
    }
}
