package pl.edu.pw.elka.pfus.eds.domain.session;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import javax.servlet.ServletContext;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Klasa helper dla hibernate używanego natywnie - bez ledge.
 */
public class HibernateUtil {
    private static HibernateUtil instance;
    private static ServletContext servletContext;
    private org.hibernate.SessionFactory sessionFactory;

    private HibernateUtil() {
        URL hibernateConfigUrl = null;
        try {
            hibernateConfigUrl = servletContext.getResource("/config/org.objectledge.hibernate.HibernateSessionFactory.xml");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        sessionFactory = new Configuration().configure(hibernateConfigUrl).buildSessionFactory();
    }

    public synchronized static HibernateUtil getInstance() {
        if(instance == null)
            instance = new HibernateUtil();
        return instance;
    }

    public static void setServletContext(ServletContext servletContext) {
        HibernateUtil.servletContext = servletContext;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}
