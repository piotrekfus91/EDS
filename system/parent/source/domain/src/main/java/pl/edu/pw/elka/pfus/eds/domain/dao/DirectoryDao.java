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

    /**
     * Zwraca wszystkie katalogi danego katalogu.
     *
     * @param directory wybrany katalog.
     * @return podkatalogi.
     */
    List<Directory> getSubdirectories(Directory directory);

    /**
     * Zwraca wszystkie katalogi danego katalogu.
     *
     * @param directoryId id katalogu.
     * @return podkatalogi.
     */
    List<Directory> getSubdirectories(int directoryId);

    /**
     * Znajduje podany katalog wraz z podkatalogami i właścicielem.
     * Katalogi tylko bieżącego katalogu, nie w głąb.
     *
     * @param directoryId id katalogu.
     * @return wypełniony katalog.
     */
    Directory getDirectoryWithSubdirectoriesAndOwner(int directoryId);

    /**
     * Znajduje podany katalog, jego podkatalogi, właściciela i pliki.
     * Podkatalogi tylko bieżącego katalogu, nie w głąb.
     *
     * @param directoryId id katalogu.
     * @return wypełniony katalog.
     */
    Directory getDirectoryWithSubdirectoriesDocumentsAndOwner(int directoryId);
}
