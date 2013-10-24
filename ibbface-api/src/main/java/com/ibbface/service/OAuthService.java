package com.ibbface.service;

import com.ibbface.interfaces.oauth.OAuthAccountPasswordToken;
import org.apache.shiro.subject.Subject;

/**
 * @author Fuchun
 * @since 1.0
 */
public interface OAuthService {

    public String completeLogin(Subject subject, OAuthAccountPasswordToken token);

    public String createCode(String clientId, Long userId);

    public String getAccessToken(String clientId, Long userId, String code);
}
