package com.ibbface.domain.generated.user;

import com.google.common.collect.ImmutableMap;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import javax.annotation.Generated;
import java.util.Map;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import static com.ibbface.domain.model.user.UserDigital.*;


/**
 * QUserDigital is a Querydsl query type for QUserDigital
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QUserDigital extends com.mysema.query.sql.RelationalPathBase<QUserDigital> {

    private static final long serialVersionUID = 1714641048;

    public static final String USER_DIGITAL_TABLE_NAME = "IBB_USER_DIGITAL";
    public static final QUserDigital UserDigital = new QUserDigital(USER_DIGITAL_TABLE_NAME);

    public final NumberPath<Long> userId = createNumber("USER_ID", Long.class);

    public final StringPath userName = createString("USER_NAME");

    public final NumberPath<Long> totalScore = createNumber("TOTAL_SCORE", Long.class);

    public final NumberPath<Long> userScore = createNumber("USER_SCORE", Long.class);

    public final NumberPath<Integer> userGrade = createNumber("USER_GRADE", Integer.class);

    public final NumberPath<Long> totalAmount = createNumber("TOTAL_AMOUNT", Long.class);

    public final NumberPath<Long> balance = createNumber("BALANCE", Long.class);

    public final NumberPath<Integer> threads = createNumber("THREADS", Integer.class);

    public final NumberPath<Integer> posts = createNumber("POSTS", Integer.class);

    public final NumberPath<Integer> postsReplies = createNumber("POSTS_REPLIES", Integer.class);

    public final NumberPath<Integer> newsComments = createNumber("NEWS_COMMENTS", Integer.class);

    public final NumberPath<Integer> imageComments = createNumber("IMAGE_COMMENTS", Integer.class);

    public final NumberPath<Integer> imageCommentReplies = createNumber("IMAGE_COMMENT_REPLIES", Integer.class);

    public final DateTimePath<java.sql.Timestamp> lastModifiedTime = createDateTime("LAST_MODIFIED_TIME", java.sql.Timestamp.class);

    public final com.mysema.query.sql.PrimaryKey<QUserDigital> primary = createPrimaryKey(userId);

    public QUserDigital(String variable) {
        super(QUserDigital.class, forVariable(variable), "null", USER_DIGITAL_TABLE_NAME);
    }

    @SuppressWarnings("all")
    public QUserDigital(Path<? extends QUserDigital> path) {
        super((Class) path.getType(), path.getMetadata(), "null", USER_DIGITAL_TABLE_NAME);
    }

    public QUserDigital(PathMetadata<?> metadata) {
        super(QUserDigital.class, metadata, "null", USER_DIGITAL_TABLE_NAME);
    }

    public final Map<String, Path<?>> propPathMap = buildPropPathMap();

    public final Path<?>[] allColumns = new Path<?>[]{
            userId, userName, totalScore, userScore, userGrade, totalAmount,
            balance, threads, posts, postsReplies, newsComments, imageComments,
            imageCommentReplies, lastModifiedTime
    };

    private Map<String, Path<?>> buildPropPathMap() {
        ImmutableMap.Builder<String, Path<?>> builder = ImmutableMap.builder();
        return builder.put(PROP_USER_ID, userId)
                .put(PROP_USER_NAME, userName)
                .put(PROP_TOTAL_SCORE, totalScore)
                .put(PROP_USER_SCORE, userScore)
                .put(PROP_USER_GRADE, userGrade)
                .put(PROP_TOTAL_AMOUNT, totalAmount)
                .put(PROP_BALANCE, balance)
                .put(PROP_THREADS, threads)
                .put(PROP_POSTS, posts)
                .put(PROP_POSTS_REPLIES, postsReplies)
                .put(PROP_NEWS_COMMENTS, newsComments)
                .put(PROP_IMAGE_COMMENTS, imageComments)
                .put(PROP_IMAGE_COMMENT_REPLIES, imageCommentReplies)
                .put(PROP_LAST_MODIFIED_TIME, lastModifiedTime)
                .build();
    }
}

