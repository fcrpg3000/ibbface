package com.ibbface.domain.model.user;

import com.google.common.base.Joiner;
import com.ibbface.domain.shared.AbstractValueObject;
import com.ibbface.util.RandomStrings;

import static com.google.common.base.CharMatcher.DIGIT;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * The user token value object class. Contains user id and access token string.
 *
 * @author Fuchun
 * @since 1.0
 */
public class UserToken extends AbstractValueObject<UserToken> {
    private static final long serialVersionUID = 1L;

    private static final char[] ACCESS_TOKEN_CHARS = {
            '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
    };

    public static final int TOKEN_LENGTH = 20;

    /**
     * Creates a new {@link UserToken} with the specified {@code userId}.
     * The {@code accessToken} is generated randomly.
     *
     * @param userId the specified user id.
     * @return A new {@link UserToken}.
     */
    public static UserToken newToken(final Long userId) {
        return new UserToken(userId);
    }

    /**
     * Parse the {@code accessToken} and return a {@link UserToken}.
     *
     * @param accessToken the access token string.
     * @return a {@code UserToken}.
     */
    public static UserToken of(String accessToken) {
        return new UserToken(accessToken);
    }

    /**
     * Construct a {@link UserToken} with the specified {@code userId} and {@code accessToken}.
     *
     * @param userId the user id.
     * @param accessToken access token string.
     * @return a {@code UserToken}.
     */
    public static UserToken of(final Long userId, final String accessToken) {
        return new UserToken(userId, accessToken);
    }

    private final Long userId;
    private final String accessToken;

    UserToken(Long userId) {
        this.userId = userId;

        final String userIdStr = String.valueOf(userId);
        int rdnStrLen = TOKEN_LENGTH - userIdStr.length() - 1;
        accessToken = Joiner.on("").join(RandomStrings.random(rdnStrLen, ACCESS_TOKEN_CHARS),
                (userId % 2 == 0 ? "I" : "l"), userId);
    }

    UserToken(String accessToken) {
        this.accessToken = accessToken;
        this.userId = findUserId(accessToken);
    }

    UserToken(Long userId, String accessToken) {
        this.userId = userId;
        this.accessToken = accessToken;
    }

    private static Long findUserId(final String accessToken) {
        if (accessToken.contains("I") || accessToken.contains("l")) {
            String userIdStr = accessToken.split("[Il]")[1];
            return isNullOrEmpty(userIdStr) || !DIGIT.matchesAllOf(userIdStr) ?
                    null : Long.valueOf(userIdStr);
        }
        return null;
    }

    public Long getUserId() {
        return userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserToken)) return false;

        UserToken userToken = (UserToken) o;

        return !(userId != null ? !userId.equals(userToken.userId) : userToken.userId != null) &&
                !(accessToken != null ? !accessToken.equals(userToken.accessToken) : userToken.accessToken != null);

    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (accessToken != null ? accessToken.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("UserToken{userId=%d, accessToken='%s'}", getUserId(), getAccessToken());
    }
}
