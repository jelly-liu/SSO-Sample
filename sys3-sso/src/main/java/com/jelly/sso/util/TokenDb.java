package com.jelly.sso.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.jelly.sso.module.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by jelly on 2016-9-21.
 */
public class TokenDb {
    private static final Logger log = LoggerFactory.getLogger(TokenDb.class);

    private static Cache<String, User> tokenCache;

    static {
        tokenCache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(GlobalConf.Token_Expired_Time_Seconds, TimeUnit.SECONDS)
                .build();
    }

    public static User getUser(String token){
        return tokenCache.getIfPresent(token);
    }

    public static void addToken(String token, User user){
        log.debug("add token={}", token);
        tokenCache.put(token, user);
    }
}
