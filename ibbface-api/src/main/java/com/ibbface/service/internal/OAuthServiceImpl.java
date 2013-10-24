package com.ibbface.service.internal;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.ibbface.domain.model.user.User;
import com.ibbface.domain.model.user.UserOnline;
import com.ibbface.domain.model.user.UserToken;
import com.ibbface.interfaces.oauth.OAuthAccountPasswordToken;
import com.ibbface.repository.user.UserOnlineRepository;
import com.ibbface.service.OAuthService;
import com.ibbface.util.RandomStrings;
import org.joda.time.DateTime;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author Fuchun
 * @since 1.0
 */
@Service("oAuthService")
public class OAuthServiceImpl implements OAuthService {

    private static final Cache<String, String> BACKUP_CACHE;

    static {
        BACKUP_CACHE = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(10000).initialCapacity(100)
                .build();
    }

    private UserOnlineRepository userOnlineRepository;
    private RedisTemplate<String, String> stringRedisTemplate;

    protected String getCodeCacheKey(String clientId, Long userId) {
        return String.format("code_%s_%s", clientId, userId);
    }

    protected String getTokenCacheKey(String authCode) {
        return String.format("token_%s", authCode);
    }

    @Override
    public String completeLogin(org.apache.shiro.subject.Subject subject, OAuthAccountPasswordToken token) {
        User user = (User) subject.getPrincipal();

        // update UserOnline
        UserOnline userOnline = userOnlineRepository.findByUserId(user.getUserId());
        userOnline.setPrevClientIp(userOnline.getLastClientIp());
        userOnline.setPrevLoginTime(userOnline.getLastLoginTime());
        userOnline.setToken(UserToken.newToken(user.getUserId()));
        userOnline.setLastClientIp(token.getHost());
        DateTime dateTime = DateTime.now();
        DateTime lastAccessedTime = new DateTime(userOnline.getLastAccessedTime());
        userOnline.setLastLoginTime(dateTime.toDate());
        if (lastAccessedTime.getYearOfEra() == dateTime.getYearOfEra() &&
                lastAccessedTime.getDayOfYear() == dateTime.getDayOfYear()) { // same day
            userOnline.setThatLoginCount(userOnline.getThatLoginCount() + 1);
        } else { // not same day
            userOnline.setThatOnlineTime(0L); // reset 0
            userOnline.setThatLoginCount(1);
        }
        userOnline.setTotalLoginCount(userOnline.getTotalLoginCount() + 1);
        userOnlineRepository.save(userOnline);

        String authCode = createCode(token.getoAuthParameter().getClientId(), user.getUserId());
        String accessToken = userOnline.getToken().getAccessToken();
        String tokenKey = getTokenCacheKey(authCode);
        try {
            stringRedisTemplate.opsForValue().set(tokenKey, accessToken, 10, TimeUnit.MINUTES);
        } catch (Exception ex) {
            // redis cache error (to get from database next time)
        }
        return authCode;
    }

    @Override
    public String createCode(String clientId, Long userId) {
        String newCode = RandomStrings.randomAlphanumeric(16);
        String cacheKey = getCodeCacheKey(clientId, userId);
        try {
            stringRedisTemplate.opsForValue().set(cacheKey, newCode, 10, TimeUnit.MINUTES);
        } catch (Exception ex) {
            // redis cache error
            BACKUP_CACHE.put(cacheKey, newCode);
        }
        return newCode;
    }

    @Override
    public String getAccessToken(String clientId, Long userId, String code) {
        String cacheKey = getCodeCacheKey(clientId, userId);
        String codeInCache;
        try {
            codeInCache = stringRedisTemplate.opsForValue().get(cacheKey);
        } catch (Exception ex) {
            // redis cache error
            codeInCache = BACKUP_CACHE.getIfPresent(cacheKey);
        }
        if (codeInCache == null) { // code not found, maybe expired?
            return null;
        }
        if (codeInCache.equals(code)) {
            UserToken userToken = UserToken.newToken(userId);
            UserOnline userOnline = userOnlineRepository.findByUserId(userId);
            userOnline.setToken(userToken);
        }
        return null;
    }

    @Resource
    public void setUserOnlineRepository(UserOnlineRepository userOnlineRepository) {
        this.userOnlineRepository = userOnlineRepository;
    }

    @Resource
    public void setStringRedisTemplate(RedisTemplate<String, String> stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }
}
