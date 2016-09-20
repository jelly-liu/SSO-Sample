package com.jelly.sso.listener;

import com.jelly.sso.util.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by jelly on 2016-9-20.
 */
public class MyServletContextListener implements ServletContextListener {
    private static final Logger log = LoggerFactory.getLogger(MyServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute(Const.SYS_NAME, Const.config.getProperty(Const.SYS_NAME));
        log.debug("ServletContext init, sysName={}", servletContext.getAttribute(Const.SYS_NAME));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
