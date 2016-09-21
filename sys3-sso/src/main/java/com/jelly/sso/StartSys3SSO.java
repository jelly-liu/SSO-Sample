package com.jelly.sso;

import com.jelly.sso.servlet.LoginOnServlet;
import com.jelly.sso.servlet.TokenValidateServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Created by jelly on 2016-9-20.
 */
public class StartSys3SSO {
    public static void main(String[] args) throws Exception {
        Server server = new Server(9093);

        String webApp = "./sys3-sso/target/sys3-sso";
        WebAppContext context = new WebAppContext();
        context.setDescriptor(webApp + "/WEB-INF/web.xml");
        context.setResourceBase(webApp);
        context.setContextPath("/");
        context.setClassLoader(Thread.currentThread().getContextClassLoader());
        server.setHandler(context);

        context.addServlet(TokenValidateServlet.class, "/tokenValid");
        context.addServlet(LoginOnServlet.class, "/loginOn");

        server.start();
        server.join();
    }
}
