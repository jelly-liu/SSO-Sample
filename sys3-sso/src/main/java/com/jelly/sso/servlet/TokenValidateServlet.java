package com.jelly.sso.servlet;

import com.jelly.sso.util.GlobalConf;
import com.jelly.sso.module.User;
import com.jelly.sso.util.TokenDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jelly on 2016-9-20.
 */
public class TokenValidateServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(TokenValidateServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter(GlobalConf.PARAM_Token);
        User user = TokenDb.getUser(token);
        if(user != null){
            resp.getWriter().write(user.toJson());
        }
        log.debug("token valid, token={}, containsToken={}", token, user);
    }
}
