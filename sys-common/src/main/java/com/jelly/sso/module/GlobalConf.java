package com.jelly.sso.module;

import java.nio.charset.Charset;

/**
 * Created by jelly on 2016-9-21.
 */
public class GlobalConf {
    public static final String CHAR_SET = "UTF-8";

    public static final String PARAM_Token = "J_TOKEN";
    public static final String PARAM_Token_User = "J_TOKEN_USER";
    public static final String PARAM_RETURN_RUL = "returnURL";

    public static final int Token_Expired_Time_Seconds = 60*30;

    public static final String path_web_inf = "/WEB-INF/";
    public static final String path_pages = "/WEB-INF/pages/";
    public static final String path_admin = "/WEB-INF/pages/admin/";

    public static final String SSO_SERVER_LOGIN_ON_URL = "http://www.sys3-sso.com:9093/loginOn";
    public static final String SSO_SERVER_TOKEN_VALID_URL = "http://www.sys3-sso.com:9093/tokenValid";
}
