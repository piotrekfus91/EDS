package pl.edu.pw.elka.pfus.eds.logic.directory;

import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;

/**
 * Interfejs do manipulacji katalogami.
 */
public interface DirectoryModifier {
    /**
     * Tworzy nowy podkatalog w katalogu zadanym poprzez id.
     *
     * @param parentDirectoryId id katalogu nadrzędnego.
     * @param name nazwa katalogu nadrzędnego.
     * @return utworzony katalog.
     */
    Directory add(int parentDirectoryId, String name);

    /**
     * Przenosi wybrany katalog do podanego katalogu docelowego.
     *
     * @param directoryId id katalogu do przeniesienia.
     * @param destinationDirectoryId id katalogu docelowego.
     */
    void move(int directoryId, int destinationDirectoryId);

    /**
     * Usuwa podany katalog wraz z podkatalogami i plikami.
     *
     * @param directory wybrany katalog.
     * @return katalog nadrzędny, jeśli istnieje, w przeciwnym wypadku null.
     */
    Directory delete(Directory directory);

    /**
     * Usuwa podany katalog wraz z podkatalogami i plikami.
     *
     * @param id id katalogu.
     * @return katalog nadrzędny, jeśli istnieje, w przeciwnym wypadku null.
     */
    Directory delete(int id);

    /**
     * Zmienia nazwę podanego katalogu.
     *
     * @param id id katalogu.
     * @param newName nowa nazwa.
     * @return zmieniony katalog.
     */
    Directory rename(int id, String newName);
}
