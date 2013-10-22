package com.ibbface.interfaces.oauth;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.ibbface.util.RandomStrings;
import com.ibbface.util.turple.Pair;
import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Map;

import static com.ibbface.interfaces.oauth.OAuthParameter.*;
import static org.junit.Assert.*;

/**
 * The {@link OAuthParameter} class test case.
 *
 * @author Fuchun
 * @since 1.0
 */
public class OAuthParameterTest {

    private static final String CUSTOM_CLIENT_ID = "12345";
    private static final String CUSTOM_CLIENT_SECRET = RandomStrings.randomAlphanumeric(16);
    private MockHttpServletRequest request;

    @Before
    public void setUp() throws Exception {
        request = new MockHttpServletRequest();
        request.setMethod("GET");
        request.setCharacterEncoding(Charsets.UTF_8.displayName());
        request.addHeader("Content-Type", "text/html;charset=UTF-8");
    }

    /**
     * OAuth authorize parameters test.
     */
    @Test
    public void testFromRequest() throws Exception {
//        String authorization = String.format("Basic %s",
//                Base64.encodeBase64String(Joiner.on(":").join(CUSTOM_CLIENT_ID, CUSTOM_CLIENT_SECRET).getBytes()));
//        request.addHeader("Authorization", authorization);

        Map<String, String> parameters = Maps.newHashMap();
        parameters.put(PARAM_CLIENT_ID, CUSTOM_CLIENT_ID);
        parameters.put(PARAM_RESPONSE_TYPE, PARAM_CODE);
        parameters.put(PARAM_REDIRECT_URI, "https://api.ibbface.com/oauth/after_bind");
        parameters.put(PARAM_STATE, RandomStrings.randomAlphanumeric(16));
        request.addParameters(parameters);

        OAuthParameter oAuthParameter = OAuthParameter.fromRequest(request);

        assertEquals(oAuthParameter.getClientId(), request.getParameter(PARAM_CLIENT_ID));
        assertNull(oAuthParameter.getClientSecret());
        assertEquals(oAuthParameter.getResponseType(), request.getParameter(PARAM_RESPONSE_TYPE));
        assertEquals(oAuthParameter.getRedirectUri(), request.getParameter(PARAM_REDIRECT_URI));
        assertEquals(oAuthParameter.getState(), request.getParameter(PARAM_STATE));
        assertNull(oAuthParameter.getGrantType());
    }

    /**
     * OAuth access_token api parameters test.
     */
    @Test
    public void testFromRequestAccessToken() throws Exception {
        String authorization = String.format("Basic %s",
        Base64.encodeBase64String(Joiner.on(":").join(CUSTOM_CLIENT_ID, CUSTOM_CLIENT_SECRET).getBytes()));
        request.addHeader("Authorization", authorization);

        Map<String, String> parameters = Maps.newHashMap();
        parameters.put(PARAM_REDIRECT_URI, "https://api.ibbface.com/oauth/access_token");
        parameters.put(PARAM_GRANT_TYPE, "authorization_code");
        parameters.put(PARAM_CODE, RandomStrings.randomAlphanumeric(16));
        request.addParameters(parameters);

        OAuthParameter oAuthParameter = OAuthParameter.fromRequest(request);

        assertEquals(oAuthParameter.getClientId(), CUSTOM_CLIENT_ID);
        assertEquals(oAuthParameter.getClientSecret(), CUSTOM_CLIENT_SECRET);
        assertEquals(oAuthParameter.getRedirectUri(), request.getParameter(PARAM_REDIRECT_URI));
        assertEquals(oAuthParameter.getCode(), request.getParameter(PARAM_CODE));
        assertEquals(oAuthParameter.getGrantType(), request.getParameter(PARAM_GRANT_TYPE));

        assertNull(oAuthParameter.getState());
        assertNull(oAuthParameter.getResponseType());
    }

    /**
     * Authorization =&gt; Basic {client_id}:{client_secret}
     */
    @Test
    public void testBasicParserAll() {
        String authorization = String.format("Basic %s",
                Base64.encodeBase64String(Joiner.on(":").join(
                        CUSTOM_CLIENT_ID, CUSTOM_CLIENT_SECRET).getBytes()));
        request.addHeader("Authorization", authorization);
        Pair<String, String> clientPair = OAuthParameter.getClientAuth(request);

        assertNotNull(clientPair);
        assertEquals(clientPair.getLeft(), CUSTOM_CLIENT_ID);
        assertEquals(clientPair.getRight(), CUSTOM_CLIENT_SECRET);
    }

    /**
     * Authorization =&gt; Basic {client_id}
     */
    @Test
    public void testBasicParser1() {
        String authorization = String.format("Basic %s",
                Base64.encodeBase64String(CUSTOM_CLIENT_ID.getBytes()));
        request.addHeader("Authorization", authorization);
        Pair<String, String> clientPair = OAuthParameter.getClientAuth(request);

        assertNotNull(clientPair);
        assertNull(clientPair.getRight());
        assertEquals(clientPair.getLeft(), CUSTOM_CLIENT_ID);
    }

    /**
     * Authorization =&gt; Basic {client_id}:
     */
    @Test
    public void testBasicParser2() {
        String authorization = String.format("Basic %s:",
                Base64.encodeBase64String(CUSTOM_CLIENT_ID.getBytes()));
        request.addHeader("Authorization", authorization);
        Pair<String, String> clientPair = OAuthParameter.getClientAuth(request);

        assertNotNull(clientPair);
        assertNull(clientPair.getRight());
        assertEquals(clientPair.getLeft(), CUSTOM_CLIENT_ID);
    }

    @Test
    public void testBasicParserError() {
        Pair<String, String> clientPair = OAuthParameter.getClientAuth(request);
        assertNull(clientPair);
    }
}
