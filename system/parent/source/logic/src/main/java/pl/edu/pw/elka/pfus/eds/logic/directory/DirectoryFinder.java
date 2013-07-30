package pl.edu.pw.elka.pfus.eds.logic.directory;

import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.FileSystemEntry;

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

    /**
     * Zwraca listę wszystkich elementów typu {@link FileSystemEntry}
     * dla podanego katalogu. Jednocześnie sprawdza właściciela.
     * Jeśli zalogowany użytkownik nie jest właścicielem
     * to zwraca listę pustą.
     *
     * @param directoryId id katalogu.
     * @return lista elementów systemu plików.
     */
    List<FileSystemEntry> getFileSystemEntries(int directoryId);

    /**
     * Zwraca katalog na podstawie podanego id.
     *
     * @param id id katalogu.
     * @return katalog.
     */
    Directory getById(int id);
}
