package com.jelly.sso.servlet;

import com.jelly.sso.module.User;
import com.jelly.sso.util.Const;
import com.jelly.sso.util.GlobalConf;
import com.jelly.sso.util.UserDb;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jelly on 2016-9-20.
 */
public class LoginOnServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(LoginOnServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String returnUrl = req.getParameter(GlobalConf.PARAM_RETURN_RUL);
        if(StringUtils.isNotEmpty(returnUrl)){
            req.setAttribute(GlobalConf.PARAM_RETURN_RUL, returnUrl);
        }


        String userName = req.getParameter(Const.param_userMame);
        String passWord = req.getParameter(Const.param_passWord);

        if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(passWord)){
            req.setAttribute(Const.error_key, "userName or passWord is empty");
            gotoLoginOn(req, resp);
            return;
        }

        //valid user
        User user = UserDb.findUser(userName, passWord);
        if(user == null){
            req.setAttribute(Const.error_key, "userName and passWord not match");
            gotoLoginOn(req, resp);
            return;
        }
        req.setAttribute(Const.error_key, "!!!success!!!");

        user.setPassword(null);
        Cookie cookie = new Cookie(GlobalConf.PARAM_Token, user.toJson());
        cookie.setMaxAge(GlobalConf.Token_Expired_Time_Seconds * 1000);
        resp.addCookie(cookie);
        req.getRequestDispatcher(GlobalConf.path_pages + "loginSuccess.jsp").forward(req, resp);
    }

    private void gotoLoginOn(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(GlobalConf.path_pages + "loginOn.jsp").forward(req, resp);
    }
}
