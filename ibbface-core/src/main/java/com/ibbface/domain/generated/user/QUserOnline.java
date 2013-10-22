/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) QUserOnline.java 2013-08-06 00:18
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

import static com.ibbface.domain.model.user.UserOnline.*;
import static com.mysema.query.types.PathMetadataFactory.forVariable;

/**
 * QUserOnline is a Querydsl query type for QUserOnline
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QUserOnline extends com.mysema.query.sql.RelationalPathBase<QUserOnline> {

    private static final long serialVersionUID = 1L;

    public static final String USER_ONLINE_TABLE_NAME = "IBB_USER_ONLINE";

    public static final QUserOnline qUserOnline = new QUserOnline(USER_ONLINE_TABLE_NAME);

    public final NumberPath<Long> userId = createNumber("USER_ID", Long.class);

    public final StringPath sessionId = createString("SESSION_ID");

    public final StringPath accessToken = createString("ACCESS_TOKEN");

    public final StringPath prevClientIp = createString("PREV_CLIENT_IP");

    public final StringPath lastClientIp = createString("LAST_CLIENT_IP");

    public final DateTimePath<Timestamp> prevLoginTime = createDateTime("PREV_LOGIN_TIME", Timestamp.class);

    public final DateTimePath<Timestamp> lastLoginTime = createDateTime("LAST_LOGIN_TIME", Timestamp.class);

    public final NumberPath<Integer> totalLoginCount = createNumber("TOTAL_LOGIN_COUNT", Integer.class);

    public final NumberPath<Integer> thatLoginCount = createNumber("THAT_LOGIN_COUNT", Integer.class);

    public final NumberPath<Long> totalOnlineTime = createNumber("TOTAL_ONLINE_TIME", Long.class);

    public final NumberPath<Long> thatOnlineTime = createNumber("THAT_ONLINE_TIME", Long.class);

    public final NumberPath<Long> lastAccessedTime = createNumber("LAST_ACCESSED_TIME", Long.class);

    public final PrimaryKey<QUserOnline> primary = createPrimaryKey(userId);

    public final Path<?>[] sequenceColumns = new Path<?>[]{
            userId, sessionId, accessToken, prevClientIp, lastClientIp, prevLoginTime,
            lastLoginTime, totalLoginCount, thatLoginCount, totalOnlineTime, thatOnlineTime,
            lastAccessedTime
    };

    public final Path<?>[] columnsWithoutId = Arrays.copyOfRange(
            sequenceColumns, 1, sequenceColumns.length);

    public final Map<String, Path<?>> propPathMap = buildPropPathMap();

    public QUserOnline(String variable) {
        super(QUserOnline.class, forVariable(variable), "null", USER_ONLINE_TABLE_NAME);
    }

    @SuppressWarnings("all")
    public QUserOnline(Path<? extends QUserOnline> path) {
        super((Class) path.getType(), path.getMetadata(), "null", USER_ONLINE_TABLE_NAME);
    }

    public QUserOnline(PathMetadata<?> metadata) {
        super(QUserOnline.class, metadata, "null", USER_ONLINE_TABLE_NAME);
    }

    private Map<String, Path<?>> buildPropPathMap() {
        ImmutableMap.Builder<String, Path<?>> builder = ImmutableMap.builder();
        return builder.put(PROP_USER_ID, userId)
                .put(PROP_SESSION_ID, sessionId)
                .put(PROP_ACCESS_TOKEN, accessToken)
                .put(PROP_PREV_CLIENT_IP, prevClientIp)
                .put(PROP_LAST_CLIENT_IP, lastClientIp)
                .put(PROP_PREV_LOGIN_TIME, prevLoginTime)
                .put(PROP_LAST_LOGIN_TIME, lastLoginTime)
                .put(PROP_TOTAL_LOGIN_COUNT, totalLoginCount)
                .put(PROP_THAT_LOGIN_COUNT, thatLoginCount)
                .put(PROP_TOTAL_ONLINE_TIME, totalOnlineTime)
                .put(PROP_THAT_ONLINE_TIME, thatOnlineTime)
                .put(PROP_LAST_ACCESSED_TIME, lastAccessedTime)
                .build();
    }
}
