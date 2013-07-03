package pl.edu.pw.elka.pfus.eds.security;

import org.objectledge.context.Context;
import org.objectledge.hibernate.HibernateSessionFactory;
import org.objectledge.security.DataBackend;
import org.objectledge.security.object.hibernate.HibernateDataBackend;
import pl.edu.pw.elka.pfus.eds.security.exception.SecurityInitializationException;

public class SecurityFacadeImpl implements SecurityFacade {
    private DataBackend dataBackend;

    public SecurityFacadeImpl(Context context, HibernateSessionFactory hibernateSessionFactory) {
        try {
            dataBackend = new HibernateDataBackend(context, hibernateSessionFactory);
        } catch (Exception e) {
            throw new SecurityInitializationException(e);
        }
    }

    @Override
    public void createUser(String login, String firstName, String lastName, String password) {
        try {
            dataBackend.createAccount(login, firstName, lastName, password);
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }
}
