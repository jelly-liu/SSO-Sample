package com.jelly.sso.filter;

import com.jelly.sso.util.Const;
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
import java.nio.charset.Charset;

/**
 * Created by jelly on 2016-9-20.
 */
public class MyFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(MyFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)servletRequest;
        HttpServletResponse res = (HttpServletResponse)servletResponse;

        String contextPath = req.getServletPath();
        log.debug("filter, servletPath={}", contextPath);

        if(StringUtils.equals(contextPath, "/") || !StringUtils.startsWith(contextPath, "/admin")){
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            if(isLoginOn(req, res)){
                filterChain.doFilter(servletRequest, servletResponse);
            }else{
                String ssoUrl = generateSSOUrl(req);
                res.sendRedirect(ssoUrl);
            }
        }
    }

    @Override
    public void destroy() {

    }

    private String generateSSOUrl(HttpServletRequest req){
        String ssoUrl = "";
        try {
            ssoUrl = Const.SYS_SSO_URL + "?";
            String loginId = req.getParameter(Const.LOGIN_ID);
            if(StringUtils.isNotEmpty(loginId))ssoUrl += Const.LOGIN_ID + "=" + loginId;
            ssoUrl += "&" + Const.RETURN_URL + "=" + URLEncoder.encode(req.getRequestURI(), "UTF-8");
        }catch (Exception e){
            log.error("generate sso url error", e);
        }
        return ssoUrl;
    }

    private boolean isLoginOn(HttpServletRequest req, HttpServletResponse res){
        String returnFromSSOStr = req.getParameter(Const.RETURN_FROM_SSO);
        if(StringUtils.isNotEmpty(returnFromSSOStr)){
            String loginValue = req.getParameter(Const.LOGIN_ID);
            //TODO, call SSO valid interface to check token
        }

        Cookie[] cookies = req.getCookies();

        //check from cookies
        if(ArrayUtils.isEmpty(cookies)){
            return false;
        }

        for(Cookie cookie : cookies){
            String name = cookie.getName();
            String value = cookie.getValue();
            log.debug("check login on, from cookie, name={}, value={}", name, value);
            if(StringUtils.equals(name, Const.LOGIN_ID)){
                if(StringUtils.isNotEmpty(value)){
                    log.debug("check login on, from cookie, login on already, find login on, name={}, value={}", name, value);
                    return true;
                }
            }
        }

        return false;
    }
}
