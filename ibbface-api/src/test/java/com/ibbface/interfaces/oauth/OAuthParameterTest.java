package com.ibbface.interfaces.oauth;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.ibbface.util.RandomStrings;
import com.ibbface.util.turple.Pair;
import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * The {@link OAuthParameter} class test case.
 *
 * @author Fuchun
 * @since 1.0
 */
public class OAuthParameterTest {

    private static final String CUSTOM_CLIENT_ID = "12345";
    private static final String CUSTOM_CLIENT_SECRET = RandomStrings.randomAlphanumeric(16);
    private HttpServletRequest request;

    @Before
    public void setUp() throws Exception {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.setMethod("GET");
        String authorization = String.format("Basic %s",
                Base64.encodeBase64String(Joiner.on(":").join(CUSTOM_CLIENT_ID, CUSTOM_CLIENT_SECRET).getBytes()));
        mockRequest.addHeader("Authorization", authorization);
        mockRequest.setCharacterEncoding(Charsets.UTF_8.displayName());
        mockRequest.addHeader("Content-Type", "text/html;charset=UTF-8");
    }

    @Test
    public void testFromRequest() throws Exception {

    }

    @Test
    public void testBasicParser() {
        String clientId = "12345", clientSecret = RandomStrings.randomAlphanumeric(16);
        String basic = String.format("Basic %s",
                Base64.encodeBase64String(Joiner.on(":").join(clientId, clientSecret).getBytes()));
        Pair<String, String> clientPair = OAuthParameter.getClientInfo(basic);

        assertNotNull(clientPair);
        assertEquals(clientPair.getLeft(), clientId);
        assertEquals(clientPair.getRight(), clientSecret);
    }

    @Test
    public void testBasicParserError() {
        String errorBasic = String.format("Basic %s", RandomStrings.random(20));
        Pair<String, String> clientPair = OAuthParameter.getClientInfo(errorBasic);

        assertNull(clientPair);
    }
}
