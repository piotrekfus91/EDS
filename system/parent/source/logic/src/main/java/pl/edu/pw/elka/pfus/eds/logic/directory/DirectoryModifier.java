package pl.edu.pw.elka.pfus.eds.logic.directory;

import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;

/**
 * Interfejs do manipulacji katalogami.
 */
public interface DirectoryModifier {
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
}
