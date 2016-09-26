package com.jelly.sso.filter;

import com.jelly.sso.util.GlobalConf;
import com.jelly.sso.module.User;
import com.jelly.sso.util.HttpUtil;
import com.jelly.sso.util.StringUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by jelly on 2016-9-20.
 */
public class MyFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(MyFilter.class);

    enum Type {
        URL_PARAMETER_WITHOUT_TOKEN, URL_PARAMETER_WITH_TOKEN_AND_VALID, URL_PARAMETER_WITH_TOKEN_BUT_INVALID;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)servletRequest;
        HttpServletResponse res = (HttpServletResponse)servletResponse;

        String contextPath = req.getServletPath();
        log.debug("filter, servletPath={}", contextPath);
        String requestURL = req.getRequestURL().toString();
        log.debug("filter, requestURL={}", requestURL);

        Type type = validTokenInUrlParameter(req, res);
        switch (type){
            case URL_PARAMETER_WITH_TOKEN_AND_VALID:{
                filterChain.doFilter(servletRequest, servletResponse);
                break;
            }
            case URL_PARAMETER_WITH_TOKEN_BUT_INVALID:{
                if(StringUtils.equals(contextPath, "/") || !StringUtils.startsWith(contextPath, "/admin")){
                    filterChain.doFilter(servletRequest, servletResponse);
                }else{
                    res.sendRedirect(generateSSOUrl(req));
                }
                break;
            }
            case URL_PARAMETER_WITHOUT_TOKEN:{
                if(StringUtils.equals(contextPath, "/") || !StringUtils.startsWith(contextPath, "/admin")){
                    filterChain.doFilter(servletRequest, servletResponse);
                }else{
                    if(isLoginOnByCookie(req, res)){
                        filterChain.doFilter(servletRequest, servletResponse);
                    }else{
                        res.sendRedirect(generateSSOUrl(req));
                    }
                }
                break;
            }
        }
    }

    @Override
    public void destroy() {

    }

    private Type validTokenInUrlParameter(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String token = req.getParameter(GlobalConf.PARAM_Token);

        //valid token through api of SSO service
        if (StringUtils.isNotEmpty(token)) {
            log.debug("find token in url parameter, token={}", token);
            User user = HttpUtil.validToken(token);
            if (user == null) {
                return Type.URL_PARAMETER_WITH_TOKEN_BUT_INVALID;
            } else {
                Cookie tokenCookie = new Cookie(GlobalConf.PARAM_Token, token);
                Cookie userCookie = new Cookie(GlobalConf.PARAM_Token_User, StringUtil.encryptEncode(user.toJson()));
                addCookie(res, new Cookie[]{tokenCookie, userCookie});
                req.setAttribute(GlobalConf.PARAM_Token, token);
                req.getSession().setAttribute(GlobalConf.PARAM_Token_User, user);
                return Type.URL_PARAMETER_WITH_TOKEN_AND_VALID;
            }
        }

        return Type.URL_PARAMETER_WITHOUT_TOKEN;
    }

    private boolean isLoginOnByCookie(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Cookie[] cookies = req.getCookies();
        //check from cookies
        if(ArrayUtils.isEmpty(cookies)){
            return false;
        }

        for(Cookie cookie : cookies){
            String name = cookie.getName();
            String value = cookie.getValue();
            log.debug("check login on, from cookie, name={}, value={}", name, value);
            if(StringUtils.equals(name, GlobalConf.PARAM_Token)){
                req.setAttribute(name, value);
                return true;
            }
        }

        return false;
    }

    private String generateSSOUrl(HttpServletRequest req){
        String ssoUrl = "";
        try {
            ssoUrl = GlobalConf.SSO_SERVER_LOGIN_ON_URL + "?";
            String requestURL = req.getRequestURL().toString();
            requestURL = URLEncoder.encode(requestURL, "UTF-8");
            ssoUrl += GlobalConf.PARAM_RETURN_RUL + "=" + requestURL;
        }catch (Exception e){
            log.error("generate sso url error", e);
        }
        return ssoUrl;
    }

    private void addCookie(HttpServletResponse res, Cookie[] cookies){
        for(Cookie cookie : cookies){
            cookie.setMaxAge(GlobalConf.Token_Expired_Time_Seconds);
            cookie.setPath("/");
            res.addCookie(cookie);
        }
    }
}
