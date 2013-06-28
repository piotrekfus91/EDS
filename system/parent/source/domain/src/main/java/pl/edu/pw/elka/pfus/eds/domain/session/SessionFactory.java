package pl.edu.pw.elka.pfus.eds.domain.session;

import org.hibernate.Session;
import org.objectledge.context.Context;

/**
 * Interfejs dostarczający API do pobierania sesji na podstawie contekstu OL.
 */
public interface SessionFactory {
    /**
     * Zwraca sesję hibernate.
     *
     * @param context context OL, na podstawie którego ma być zwrócona sesja.
     * @return sesja hibernate.
     */
    Session getSession(Context context);
}
