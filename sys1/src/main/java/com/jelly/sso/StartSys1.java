package com.jelly.sso;

import com.jelly.sso.filter.MyFilter;
import com.jelly.sso.listener.MyServletContextListener;
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
        Server server = new Server(9090);

        String webapp = "./sys1/target/sys1";
        WebAppContext context = new WebAppContext();
        context.setDescriptor(webapp + "/WEB-INF/web.xml");
        context.setResourceBase(webapp);
        context.setContextPath("/");
        context.setClassLoader(Thread.currentThread().getContextClassLoader());
        server.setHandler(context);

        context.addFilter(MyFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
        context.addEventListener(new MyServletContextListener());
        context.addServlet(HelloServlet.class, "/hello");
        context.addServlet(HelloServlet.class, "/admin/hello");

        server.start();
        server.join();
    }
}
