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
    public static String SYS_NAME;
    private static final String SYS_NAME_KEY = "sysName";
    public static String SYS_SSO_URL;
    private static final String SYS_SSO_URL_KEY= "sysSSOUrl";

    public static final String ERROR_MESSAGE_KEY= "errorMessage";
    public static final String LOGIN_ID = "LOGIN_ID_9527";
    public static final String RETURN_URL = "returnURL";
    public static final String RETURN_FROM_SSO = "returnFromSSO";

    static {
        try{
            config = new Properties();
            config.load(Const.class.getResource("/config.properties").openStream());

            SYS_NAME = config.getProperty(SYS_NAME_KEY);
            SYS_SSO_URL = config.getProperty(SYS_SSO_URL_KEY);
        }catch (Exception e){
            log.error("init config error", e);
        }
    }
}
