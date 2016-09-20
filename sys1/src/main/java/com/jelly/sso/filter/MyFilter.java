package com.jelly.sso.filter;

import com.jelly.sso.util.Const;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
            req.setAttribute(Const.ERROR_MESSAGE_KEY, "login on first, then you can access admin resources");
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }

    @Override
    public void destroy() {

    }
}
