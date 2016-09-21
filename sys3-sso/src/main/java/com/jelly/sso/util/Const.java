package com.jelly.sso.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Created by jelly on 2016-9-20.
 */
public class Const {
    private static final Logger log = LoggerFactory.getLogger(Const.class);

    public static Properties config;

    public static final String error_key = "m_s_g";

    public static final String param_userMame = "userName";
    public static final String param_passWord = "passWord";

    static {
        try{
            config = new Properties();
            config.load(Const.class.getResource("/config.properties").openStream());
        }catch (Exception e){
            log.error("init config error", e);
        }
    }
}
