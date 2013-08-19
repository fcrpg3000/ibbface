package com.ibbface.domain.generated.user;

import com.google.common.collect.ImmutableMap;
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
import static com.ibbface.domain.model.user.User.*;


/**
 * QUser is a Querydsl query type for QUser
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QUser extends com.mysema.query.sql.RelationalPathBase<QUser> {

    private static final long serialVersionUID = 268427616;

    public static final String USER_TABLE_NAME = "IBB_USER";
    public static final QUser User = new QUser(USER_TABLE_NAME);

    public final NumberPath<Long> userId = createNumber("USER_ID", Long.class);

    public final StringPath email = createString("EMAIL");

    public final StringPath userName = createString("USER_NAME");

    public final StringPath userHandle = createString("USER_HANDLE");

    public final StringPath hashPassword = createString("HASH_PASSWORD");

    public final NumberPath<Short> genderCode = createNumber("GENDER_CODE", Short.class);

    public final StringPath passwordSalt = createString("PASSWORD_SALT");

    public final StringPath mobileNo = createString("MOBILE_NO");

    public final BooleanPath mobileVerified = createBoolean("MOBILE_VERIFIED");

    public final StringPath avatarUri = createString("AVATAR_URI");

    public final StringPath smallAvatarUri = createString("SMALL_AVATAR_URI");

    public final StringPath thumbAvatarUri = createString("THUMB_AVATAR_URI");

    public final StringPath spareEmail = createString("SPARE_EMAIL");

    public final StringPath roleData = createString("ROLE_DATA");

    public final BooleanPath isActivated = createBoolean("IS_ACTIVATED");

    public final BooleanPath isDisabled = createBoolean("IS_DISABLED");

    public final DateTimePath<Timestamp> disabledStart = createDateTime("DISABLED_START", Timestamp.class);

    public final DateTimePath<Timestamp> disabledEnd = createDateTime("DISABLED_END", Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> createdTime = createDateTime(
            "CREATED_TIME", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> lastModifiedTime = createDateTime("LAST_MODIFIED_TIME",
            java.sql.Timestamp.class);

    public final com.mysema.query.sql.PrimaryKey<QUser> primary = createPrimaryKey(userId);

    public final Path<?>[] sequenceColumns = new Path<?>[]{
            userId, email, userName, userHandle, hashPassword, genderCode, passwordSalt,
            mobileNo, mobileVerified, avatarUri, smallAvatarUri, thumbAvatarUri,
            spareEmail, roleData, isActivated, isDisabled, disabledStart, disabledEnd,
            createdTime, lastModifiedTime
    };

    public final Path<?>[] columnsWithoutId = Arrays.copyOfRange(
            sequenceColumns, 1, sequenceColumns.length);

    public QUser(String variable) {
        super(QUser.class, forVariable(variable), "null", USER_TABLE_NAME);
    }

    @SuppressWarnings("all")
    public QUser(Path<? extends QUser> path) {
        super((Class) path.getType(), path.getMetadata(), "null", USER_TABLE_NAME);
    }

    public QUser(PathMetadata<?> metadata) {
        super(QUser.class, metadata, "null", USER_TABLE_NAME);
    }

    public final Map<String, Path<?>> propPathMap = buildPropPathMap();

    private Map<String, Path<?>> buildPropPathMap() {
        final ImmutableMap.Builder<String, Path<?>> mapBuilder =
                ImmutableMap.builder();
        Map<String, Path<?>> propPathMap =
                mapBuilder.put(PROP_USER_ID, userId)
                        .put(PROP_EMAIL, email)
                        .put(PROP_USER_NAME, userName)
                        .put(PROP_USER_HANDLE, userHandle)
                        .put(PROP_HASH_PASSWORD, hashPassword)
                        .put(PROP_GENDER_CODE, genderCode)
                        .put(PROP_PASSWORD_SALT, passwordSalt)
                        .put(PROP_MOBILE_NO, mobileNo)
                        .put(PROP_MOBILE_VERIFIED, mobileVerified)
                        .put(PROP_AVATAR_URI, avatarUri)
                        .put(PROP_SMALL_AVATAR_URI, smallAvatarUri)
                        .put(PROP_THUMB_AVATAR_URI, thumbAvatarUri)
                        .put(PROP_SPARE_EMAIL, spareEmail)
                        .put(PROP_ROLE_DATA, roleData)
                        .put(PROP_IS_ACTIVATED, isActivated)
                        .put(PROP_IS_DISABLED, isDisabled)
                        .put(PROP_DISABLED_START, disabledStart)
                        .put(PROP_DISABLED_END, disabledEnd)
                        .put(PROP_CREATED_TIME, createdTime)
                        .put(PROP_LAST_MODIFIED_TIME, lastModifiedTime).build();
        return propPathMap;
    }
}

