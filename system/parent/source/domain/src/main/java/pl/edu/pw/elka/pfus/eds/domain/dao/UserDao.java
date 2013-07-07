package pl.edu.pw.elka.pfus.eds.domain.dao;

import pl.edu.pw.elka.pfus.eds.domain.entity.User;

/**
 * DAO dla obiektów typu {@link User}.
 */
public interface UserDao extends NamedDao<User>, IdentifableDao<User> {
    /**
     * Wyszukuje w bazie użytkownika o zadanej nazwie i haśle.
     *
     * @param name nazwa użytkownika
     * @param password hasło.
     * @return znaleziony użytkownik.
     */
    User findByNameAndPassword(String name, String password);
}
