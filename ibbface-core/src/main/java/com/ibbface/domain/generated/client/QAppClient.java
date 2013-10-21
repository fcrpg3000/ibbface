package com.ibbface.domain.generated.client;

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

import static com.ibbface.domain.model.client.base.BaseAppClient.*;
import static com.ibbface.domain.shared.PropName.PROP_ID;
import static com.mysema.query.types.PathMetadataFactory.forVariable;

/**
 * QAppClient is a Querydsl query type for QAppClient
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QAppClient extends com.mysema.query.sql.RelationalPathBase<QAppClient> {

    public static final String APP_CLIENT_TABLE = "ibb_app_client";
    public static final QAppClient aAppClient = new QAppClient(APP_CLIENT_TABLE);

    public final NumberPath<Integer> id = createNumber("ID", Integer.class);

    public final StringPath clientSecret = createString("CLIENT_SECRET");

    public final NumberPath<Short> typeCode = createNumber("TYPE_CODE", Short.class);

    public final StringPath version = createString("VERSION");

    public final StringPath versionName = createString("VERSION_NAME");

    public final StringPath upgradeContent = createString("UPGRADE_CONTENT");

    public final StringPath downloadUrls = createString("DOWNLOAD_URLS");

    public final StringPath incompatibleData = createString("INCOMPATIBLE_DATA");

    public final BooleanPath isStable = createBoolean("IS_STABLE");

    public final DateTimePath<Timestamp> publishTime = createDateTime("PUBLISH_TIME", Timestamp.class);

    public final DateTimePath<Timestamp> lastModifiedTime = createDateTime("LAST_MODIFIED_TIME", Timestamp.class);

    public final DateTimePath<Timestamp> createdTime = createDateTime("CREATED_TIME", Timestamp.class);

    public final PrimaryKey<QAppClient> primary = createPrimaryKey(id);

    public final Path<?>[] sequenceColumns = new Path<?>[] {
            id, clientSecret, typeCode, version, versionName, upgradeContent, downloadUrls,
            incompatibleData, isStable, publishTime, lastModifiedTime, createdTime
    };

    public final Path<?>[] columnsWithoutId = Arrays.copyOfRange(sequenceColumns, 1, sequenceColumns.length);

    public final Map<String, Path<?>> propPathMap = buildPropPathMap();

    private Map<String, Path<?>> buildPropPathMap() {
        ImmutableMap.Builder<String, Path<?>> builder = ImmutableMap.builder();
        builder.put(PROP_ID, id);
        builder.put(PROP_CLIENT_SECRET, clientSecret);
        builder.put(PROP_TYPE_CODE, typeCode);
        builder.put(PROP_VERSION, version);
        builder.put(PROP_VERSION_NAME, versionName);
        builder.put(PROP_UPGRADE_CONTENT, upgradeContent);
        builder.put(PROP_DOWNLOAD_URLS, downloadUrls);
        builder.put(PROP_INCOMPATIBLE_DATA, incompatibleData);
        builder.put(PROP_IS_STABLE, isStable);
        builder.put(PROP_PUBLISH_TIME, publishTime);
        builder.put(PROP_LAST_MODIFIED_TIME, lastModifiedTime);
        builder.put(PROP_CREATED_TIME, createdTime);
        return builder.build();
    }

    public QAppClient(String variable) {
        super(QAppClient.class, forVariable(variable), "null", APP_CLIENT_TABLE);
    }

    @SuppressWarnings("all")
    public QAppClient(Path<? extends QAppClient> path) {
        super((Class) path.getType(), path.getMetadata(), "null", APP_CLIENT_TABLE);
    }

    public QAppClient(PathMetadata<?> metadata) {
        super(QAppClient.class, metadata, "null", APP_CLIENT_TABLE);
    }
}
