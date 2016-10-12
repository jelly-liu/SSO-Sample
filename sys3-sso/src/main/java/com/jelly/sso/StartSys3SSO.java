package com.jelly.sso;

import com.jelly.sso.servlet.JSONPServlet;
import com.jelly.sso.servlet.LoginOnServlet;
import com.jelly.sso.servlet.TokenValidateServlet;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Created by jelly on 2016-9-20.
 */
public class StartSys3SSO {
    public static void main(String[] args) throws Exception {
//        start();
        startSSL();
    }

    private static void start() throws Exception {
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

    private static void startSSL() throws Exception {
        Server server = new Server();

        //SSL
        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath("keystore");
        sslContextFactory.setKeyStorePath("./sys3-sso/target/sys3-sso/WEB-INF/classes/keystore");
        sslContextFactory.setKeyStorePassword("123456");
        sslContextFactory.setKeyManagerPassword("123456");
        ServerConnector httpsConnector = new ServerConnector(
                server,
                new SslConnectionFactory(
                        sslContextFactory,
                        HttpVersion.HTTP_1_1.asString()
                ),
                new HttpConnectionFactory(new HttpConfiguration())
        );
        httpsConnector.setPort(9093);
        server.addConnector(httpsConnector);

        //WEB
        String webApp = "./sys3-sso/target/sys3-sso";
        WebAppContext context = new WebAppContext();
        context.setDescriptor(webApp + "/WEB-INF/web.xml");
        context.setResourceBase(webApp);
        context.setContextPath("/");
        context.setClassLoader(Thread.currentThread().getContextClassLoader());
        server.setHandler(context);

        context.addServlet(TokenValidateServlet.class, "/tokenValid");
        context.addServlet(LoginOnServlet.class, "/loginOn");
        context.addServlet(JSONPServlet.class, "/jsonp");

        server.start();
        server.join();
    }
}
