package pl.edu.pw.elka.pfus.eds.logic.tag.cache;

import org.hibernate.Session;
import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;

import java.util.List;

/**
 * Interfejs cache dla tagów.
 */
public interface TagCache {
    /**
     * Zwraca tag na podstawie id.
     *
     * @param value wartość tagu.
     * @return tag.
     */
    Tag get(String value);

    /**
     * Zwraca wszystkie tagi.
     *
     * @return lista wszystkich tagów.
     */
    List<Tag> getAll();

    /**
     * Przebugowuje cache.
     */
    void rebuild();

    /**
     * Ustawia sesję hibernate (na potrzeby ledge).
     *
     * @param session sesja hibernate.
     */
    void setSession(Session session);
}
