package com.jelly.sso.servlet;

import com.google.gson.Gson;
import com.jelly.sso.module.Jsonp;
import com.jelly.sso.module.User;
import com.jelly.sso.util.Const;
import com.jelly.sso.util.GlobalConf;
import com.jelly.sso.util.TokenDb;
import com.jelly.sso.util.UserDb;
import com.sun.org.apache.bcel.internal.generic.GOTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by jelly on 2016-9-20.
 */
public class JSONPServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(JSONPServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String callback = req.getParameter(GlobalConf.PARAM_JSONP_CALLBACK);
        if(callback == null){
            throw new RuntimeException("parameter JSONPCallback is empty");
        }

        Jsonp jsonp = new Jsonp();
        Cookie[] cookies = req.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(GlobalConf.PARAM_Token)){
                    jsonp.setFlag(true);
                    jsonp.setJson(cookie.getValue());
                    break;
                }
            }
        }

        String data = new Gson().toJson(jsonp);
        resp.getWriter().write(callback + "(" + data + ");");
    }

    private void gotoLoginOn(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(GlobalConf.path_pages + "loginOn.jsp").forward(req, resp);
    }
}
