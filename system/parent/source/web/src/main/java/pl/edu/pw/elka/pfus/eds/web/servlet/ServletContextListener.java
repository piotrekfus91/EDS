package pl.edu.pw.elka.pfus.eds.web.servlet;

import pl.edu.pw.elka.pfus.eds.domain.session.HibernateUtil;

import javax.servlet.ServletContextEvent;

public class ServletContextListener implements javax.servlet.ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        HibernateUtil.setServletContext(sce.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
