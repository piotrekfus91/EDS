package pl.edu.pw.elka.pfus.eds.domain.dao;

import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;

import java.util.List;

/**
 * DAO dla obiektów {@link Directory}
 */
public interface DirectoryDao extends IdentifableDao<Directory> {
    /**
     * Zwraca wszystkie główne katalogi użytkownika,
     * to znaczy takie, których parent jest null-em.
     *
     * @param user dany użytkownik.
     * @return główne katalogi.
     */
    List<Directory> getRootDirectories(User user);

    /**
     * Zwraca wszystkie główne katalogi użytkownika,
     * to znaczy takie, których parent jest null-em.
     *
     * @param userId id użytkownika.
     * @return główne katalogi.
     */
    List<Directory> getRootDirectories(int userId);
}
