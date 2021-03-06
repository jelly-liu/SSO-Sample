package com.jelly.sso.util;

/**
 * Created by jelly on 2016-9-21.
 */
public class GlobalConf {
    public static final String CHAR_SET = "UTF-8";

    public static final String PARAM_Token = "J_TOKEN";
    public static final String PARAM_RETURN_RUL = "returnURL";

    public static final String PARAM_JSONP_CALLBACK = "JSONPCallback";

    public static final int Token_Expired_Time_Seconds = 60*10;

    public static final String path_web_inf = "/WEB-INF/";
    public static final String path_pages = "/WEB-INF/pages/";
    public static final String path_admin = "/WEB-INF/pages/admin/";

    public static final String SSO_SERVER_LOGIN_ON_URL = "https://www.sys3-sso.com:9093/loginOn";
}
