package com.jelly.sso;

import com.jelly.sso.filter.MyFilter;
import com.jelly.sso.servlet.AdminServlet;
import com.jelly.sso.servlet.HelloServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

/**
 * Created by jelly on 2016-9-20.
 */
public class StartSys1 {
    public static void main(String[] args) throws Exception {
        startSys1();
        startSys2();
    }

    public static void startSys1() throws Exception {
        Server server = new Server(9091);

        String webapp = "./sys1/target/sys1";
        WebAppContext context = new WebAppContext();
        context.setDescriptor(webapp + "/WEB-INF/web.xml");
        context.setResourceBase(webapp);
        context.setContextPath("/");
        context.setClassLoader(Thread.currentThread().getContextClassLoader());
        server.setHandler(context);

        context.addFilter(MyFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
        context.addServlet(HelloServlet.class, "/hello");
        context.addServlet(AdminServlet.class, "/admin");

        server.start();
    }

    public static void startSys2() throws Exception {
        Server server = new Server(9092);

        String webapp = "./sys1/target/sys1";
        WebAppContext context = new WebAppContext();
        context.setDescriptor(webapp + "/WEB-INF/web.xml");
        context.setResourceBase(webapp);
        context.setContextPath("/");
        context.setClassLoader(Thread.currentThread().getContextClassLoader());
        server.setHandler(context);

        context.addFilter(MyFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
        context.addServlet(HelloServlet.class, "/hello");
        context.addServlet(AdminServlet.class, "/admin");

        server.start();
    }
}
