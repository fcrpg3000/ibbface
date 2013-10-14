/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) QChild.java 2013-09-29 17:23
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

import static com.ibbface.domain.model.user.base.BaseChild.*;
import static com.ibbface.domain.shared.PropName.PROP_CREATED_TIME;
import static com.ibbface.domain.shared.PropName.PROP_ID;
import static com.ibbface.domain.shared.PropName.PROP_LAST_MODIFIED_TIME;
import static com.ibbface.domain.shared.PropName.PROP_USER_ID;
import static com.mysema.query.types.PathMetadataFactory.forVariable;

/**
 * QUser is a Querydsl query type for QChild.
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QChild extends com.mysema.query.sql.RelationalPathBase<QChild> {
    private static final long serialVersionUID = 1L;
    public static final String CHILD_TABLE_NAME = "ibb_child";
    public static final QChild qChild = new QChild(CHILD_TABLE_NAME);

    public final NumberPath<Long> id = createNumber("ID", Long.class);
    public final NumberPath<Long> userId = createNumber("USER_ID", Long.class);
    public final StringPath petName = createString("PET_NAME");
    public final StringPath realName = createString("REAL_NAME");
    public final NumberPath<Short> genderCode = createNumber("GENDER_CODE", Short.class);
    public final DateTimePath<Timestamp> birthday = createDateTime("BIRTHDAY", Timestamp.class);
    public final StringPath lunarBirth = createString("LUNAR_BIRTH");
    public final NumberPath<Short> constellationId = createNumber("CONSTELLATION_ID", Short.class);
    public final NumberPath<Short> zodiacId = createNumber("ZODIAC_ID", Short.class);
    public final NumberPath<Short> bloodTypeId = createNumber("BLOOD_TYPE_ID", Short.class);
    public final StringPath avatarUri = createString("AVATAR_URI");
    public final StringPath smallAvatarUri = createString("SMALL_AVATAR_URI");
    public final StringPath thumbAvatarUri = createString("THUMB_AVATAR_URI");
    public final DateTimePath<Timestamp> createdTime = createDateTime(
            "CREATED_TIME", Timestamp.class);
    public final DateTimePath<Timestamp> lastModifiedTime = createDateTime("LAST_MODIFIED_TIME",
            Timestamp.class);

    public final PrimaryKey<QChild> primary = createPrimaryKey(id);

    public final Path<?>[] sequenceColumns = new Path<?>[] {
            id, userId, petName, realName, genderCode, birthday, lunarBirth,
            constellationId, zodiacId, bloodTypeId, avatarUri, smallAvatarUri,
            thumbAvatarUri, createdTime, lastModifiedTime
    };

    public final Path<?>[] columnsWithoutId = Arrays.copyOfRange(
            sequenceColumns, 1, sequenceColumns.length);

    public final Map<String, Path<?>> propPathMap = buildPropPathMap();

    public QChild(String variable) {
        super(QChild.class, forVariable(variable), "null", CHILD_TABLE_NAME);
    }

    @SuppressWarnings("all")
    public QChild(Path<? extends QChild> path) {
        super((Class) path.getType(), path.getMetadata(), "null", CHILD_TABLE_NAME);
    }

    public QChild(PathMetadata<?> metadata) {
        super(QChild.class, metadata, "null", CHILD_TABLE_NAME);
    }

    private Map<String, Path<?>> buildPropPathMap() {
        final ImmutableMap.Builder<String, Path<?>> mapBuilder =
                ImmutableMap.builder();
        Map<String, Path<?>> propPathMap =
                mapBuilder.put(PROP_ID, id)
                        .put(PROP_USER_ID, userId)
                        .put(PROP_PET_NAME, petName)
                        .put(PROP_REAL_NAME, realName)
                        .put(PROP_GENDER_CODE, genderCode)
                        .put(PROP_BIRTHDAY, birthday)
                        .put(PROP_LUNAR_BIRTH, lunarBirth)
                        .put(PROP_CONSTELLATION_ID, constellationId)
                        .put(PROP_ZODIAC_ID, zodiacId)
                        .put(PROP_BLOOD_TYPE_ID, bloodTypeId)
                        .put(PROP_AVATAR_URI, avatarUri)
                        .put(PROP_SMALL_AVATAR_URI, smallAvatarUri)
                        .put(PROP_THUMB_AVATAR_URI, thumbAvatarUri)
                        .put(PROP_CREATED_TIME, createdTime)
                        .put(PROP_LAST_MODIFIED_TIME, lastModifiedTime).build();
        return propPathMap;
    }
}
