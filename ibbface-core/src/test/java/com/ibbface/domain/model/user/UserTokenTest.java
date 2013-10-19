package com.ibbface.domain.model.user;

import org.hamcrest.core.IsNot;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * {@link UserToken} test case.
 *
 * @author Fuchun
 * @since 1.0
 */
public class UserTokenTest {

    @Test
    public void testNewToken() {
        UserToken token1 = UserToken.newToken(10001L);
        UserToken token2 = UserToken.newToken(10001L);

        assertEquals(token1.getUserId(), token2.getUserId());
        assertNotEquals(token1.getAccessToken(), token2.getAccessToken());
        assertThat(token1, IsNot.not(token2));
    }

    @Test
    public void testTokenBuild() {
        UserToken token = UserToken.newToken(10000L);

        UserToken token1 = UserToken.of(token.getAccessToken());
        assertEquals(token, token1);
        assertTrue(token.getAccessToken().contains("I") || token.getAccessToken().contains("l"));
    }
}
