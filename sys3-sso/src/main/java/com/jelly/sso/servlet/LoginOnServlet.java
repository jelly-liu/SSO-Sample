package com.jelly.sso.servlet;

import com.jelly.sso.module.GlobalConf;
import com.jelly.sso.module.User;
import com.jelly.sso.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;

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

        String tokenId = StringUtils.replace(UUID.randomUUID().toString(), "-", "");
        TokenDb.addToken(tokenId, user);
        log.debug("login on success, userName={}, passWord={}", userName, passWord);
        req.setAttribute(Const.error_key, "!!!success!!!");

        //redirect to returnUrl
        if(StringUtils.isEmpty(returnUrl)){
            log.error("userName and passWord valid, but returnUrl is empty");
            gotoLoginOn(req, resp);
            return;
        }

        returnUrl = URLDecoder.decode(returnUrl, "UTF-8");
        if(!StringUtils.contains(returnUrl, "?")){
            returnUrl += "?1=1";
        }
        returnUrl += ("&" + GlobalConf.PARAM_Token + "=" + tokenId);
//        String userJson = user.toJson();
//        String userJsonEncryptEncode = StringUtil.encryptEncode(userJson);
//        returnUrl += ("&" + GlobalConf.PARAM_TokenValue + "=" + userJsonEncryptEncode);
        resp.sendRedirect(returnUrl);
    }

    private void gotoLoginOn(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(GlobalConf.path_pages + "loginOn.jsp").forward(req, resp);
    }
}
