package pl.edu.pw.elka.pfus.eds.logic.directory;

import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;

import java.util.List;

/**
 * Interfejs dla przeszukiwania i poszukiwania katalogów.
 */
public interface DirectoryFinder {
    /**
     * Znajduje wszystkie katalogi główne bieżącego użytkownika.
     *
     * @return katalogi główne użytkownika.
     */
    List<Directory> getRootDirectories();

    /**
     * Znajduje wszystkie podkatalogi podanego katalogu.
     * Jednocześnie sprawdza, czy użytkownik jest
     * właścicielem katalogu. Jeśli nie jest, to zwraca
     * pustą listę.
     *
     * @param directory katalog nadrzędny.
     * @return lista katalogów podrzędnych
     */
    List<Directory> getSubdirectories(Directory directory);

    /**
     * Znajduje wszystkie podkatalogi podanego katalogu.
     * Jednocześnie sprawdza, czy użytkownik jest
     * właścicielem katalogu. Jeśli nie jest, to zwraca
     * pustą listę.
     *
     * @param directoryId katalog nadrzędny.
     * @return lista katalogów podrzędnych
     */
    List<Directory> getSubdirectories(int directoryId);
}
