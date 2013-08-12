package pl.edu.pw.elka.pfus.eds.domain.dao;

import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;

import java.util.List;

/**
 * DAO dla obiektów {@link Directory}
 */
public interface DirectoryDao extends IdentifableDao<Directory> {
    /**
     * Zwraca katalog główny użytkownika, to znaczy
     * taki, którego parent nie jest {@code null}.
     *
     *
     * @param user dany użytkownik.
     * @return główny katalog.
     */
    Directory getRootDirectory(User user);

    /**
     * Zwraca katalog główny użytkownika, to znaczy
     * taki, którego parent nie jest {@code null}.
     *
     *
     * @param userId id użytkownika.
     * @return główny katalog.
     */
    Directory getRootDirectory(int userId);

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
    Directory getDirectoryWithFileSystemEntriesAndOwner(int directoryId);
}
