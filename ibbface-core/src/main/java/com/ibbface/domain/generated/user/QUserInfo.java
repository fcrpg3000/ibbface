package com.ibbface.domain.generated.user;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import javax.annotation.Generated;

import static com.mysema.query.types.PathMetadataFactory.forVariable;


/**
 * QUserInfo is a Querydsl query type for QUserInfo
 * @deprecated USER_INFO table deleted.
 */
@Deprecated
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QUserInfo extends com.mysema.query.sql.RelationalPathBase<QUserInfo> {

    private static final long serialVersionUID = 1620249134;

    public static final QUserInfo UserInfo = new QUserInfo("ibb_user_info");

    public final NumberPath<Integer> bloodTypeId = createNumber("blood_type_id", Integer.class);

    public final NumberPath<Byte> calendarMode = createNumber("calendar_mode", Byte.class);

    public final NumberPath<Integer> constellationId = createNumber("constellation_id", Integer.class);

    public final DateTimePath<java.sql.Timestamp> createdTime = createDateTime("created_time", java.sql.Timestamp.class);

    public final StringPath dateOfBirth = createString("date_of_birth");

    public final NumberPath<Integer> eduId = createNumber("edu_id", Integer.class);

    public final NumberPath<Integer> feelingId = createNumber("feeling_id", Integer.class);

    public final NumberPath<Integer> htCityId = createNumber("ht_city_id", Integer.class);

    public final NumberPath<Integer> htCountryId = createNumber("ht_country_id", Integer.class);

    public final NumberPath<Integer> htDistId = createNumber("ht_dist_id", Integer.class);

    public final NumberPath<Integer> htProvinceId = createNumber("ht_province_id", Integer.class);

    public final DateTimePath<java.sql.Timestamp> lastModifiedTime = createDateTime("last_modified_time", java.sql.Timestamp.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath otherState = createString("other_state");

    public final NumberPath<Integer> porCityId = createNumber("por_city_id", Integer.class);

    public final NumberPath<Integer> porCountryId = createNumber("por_country_id", Integer.class);

    public final NumberPath<Integer> porDistId = createNumber("por_dist_id", Integer.class);

    public final NumberPath<Integer> porProvinceId = createNumber("por_province_id", Integer.class);

    public final NumberPath<Long> userId = createNumber("user_id", Long.class);

    public final StringPath userName = createString("user_name");

    public final com.mysema.query.sql.PrimaryKey<QUserInfo> primary = createPrimaryKey(userId);

    public QUserInfo(String variable) {
        super(QUserInfo.class, forVariable(variable), "null", "ibb_user_info");
    }

    @SuppressWarnings("all")
    public QUserInfo(Path<? extends QUserInfo> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "ibb_user_info");
    }

    public QUserInfo(PathMetadata<?> metadata) {
        super(QUserInfo.class, metadata, "null", "ibb_user_info");
    }

}

