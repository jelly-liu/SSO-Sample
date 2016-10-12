package com.jelly.sso.util;

import com.jelly.sso.module.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by jelly on 2016-9-21.
 */
public class UserDb {
    private static final Logger log = LoggerFactory.getLogger(UserDb.class);

    private static Set<User> userCache = new CopyOnWriteArraySet<>();

    static {
        try{
            userCache.add(new User(1001L, "tom", "123"));
            userCache.add(new User(1002L, "jerry", "123"));
        }catch (Exception e){
            log.error("init UsersDd error", e);
        }
    }

    public static User findUser(String userName, String passWord){
        for(User user : userCache){
            if(user.getName().equals(userName) && user.getPassword().equals(passWord)){
                try {
                    return user.clone();
                } catch (Exception e) {
                    log.error("clone exception", e);
                }
            }
        }
        return null;
    }
}
