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
    public List<Directory> getRootDirectories();
}
