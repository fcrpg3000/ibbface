/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) QUserSession.java 2013-08-10 12:39
 */

package com.ibbface.domain.generated.session;

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

import static com.ibbface.domain.model.session.UserSession.PROP_ACCESS_TOKEN;
import static com.ibbface.domain.model.session.UserSession.PROP_ATTRIBUTES_DATA;
import static com.ibbface.domain.model.session.UserSession.PROP_CREATION_TIME;
import static com.ibbface.domain.model.session.UserSession.PROP_IS_VALID;
import static com.ibbface.domain.model.session.UserSession.PROP_LAST_ACCESSED_TIME;
import static com.ibbface.domain.model.session.UserSession.PROP_SESSION_ID;
import static com.ibbface.domain.model.session.UserSession.PROP_USER_ID;
import static com.mysema.query.types.PathMetadataFactory.forVariable;

/**
 * QUserSession is a Querydsl query type for QUserSession
 *
 * @author Fuchun
 * @since 1.0
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QUserSession extends com.mysema.query.sql.RelationalPathBase<QUserSession> {

    private static final long serialVersionUID = 1L;

    public static final String USER_SESSION_TABLE_NAME = "ibb_user_session";

    public static final QUserSession qUserSession = new QUserSession(USER_SESSION_TABLE_NAME);

    public final NumberPath<Long> userId = createNumber("USER_ID", Long.class);

    public final StringPath sessionId = createString("SESSION_ID");

    public final StringPath accessToken = createString("ACCESS_TOKEN");

    public final StringPath attributesData = createString("ATTRIBUTES_DATA");

    public final BooleanPath isValid = createBoolean("IS_VALID");

    public final NumberPath<Long> creationTime = createNumber("CREATION_TIME", Long.class);

    public final NumberPath<Long> lastAccessedTime = createNumber("LAST_ACCESSED_TIME", Long.class);

    public final PrimaryKey<QUserSession> primary = createPrimaryKey(userId);

    public final Path<?>[] sequenceColumns = new Path<?>[]{
            userId, sessionId, accessToken, attributesData,
            isValid, creationTime, lastAccessedTime
    };

    public final Map<String, Path<?>> propPathMap = buildPropPathMap();

    public final Path<?>[] columnsWithoutId = Arrays.copyOfRange(
            sequenceColumns, 1, sequenceColumns.length);

    public QUserSession(String variable) {
        super(QUserSession.class, forVariable(variable), "null", USER_SESSION_TABLE_NAME);
    }

    @SuppressWarnings("all")
    public QUserSession(Path<? extends QUserSession> path) {
        super((Class) path.getType(), path.getMetadata(), "null", USER_SESSION_TABLE_NAME);
    }

    public QUserSession(PathMetadata<?> metadata) {
        super(QUserSession.class, metadata, "null", USER_SESSION_TABLE_NAME);
    }

    private Map<String, Path<?>> buildPropPathMap() {
        ImmutableMap.Builder<String, Path<?>> builder = ImmutableMap.builder();
        return builder.put(PROP_USER_ID, userId)
                .put(PROP_SESSION_ID, sessionId)
                .put(PROP_ACCESS_TOKEN, accessToken)
                .put(PROP_ATTRIBUTES_DATA, attributesData)
                .put(PROP_IS_VALID, isValid)
                .put(PROP_CREATION_TIME, creationTime)
                .put(PROP_LAST_ACCESSED_TIME, lastAccessedTime)
                .build();
    }
}
