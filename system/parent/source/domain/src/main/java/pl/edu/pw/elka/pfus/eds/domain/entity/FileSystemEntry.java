package pl.edu.pw.elka.pfus.eds.domain.entity;

/**
 * Oznacza encję, jako element systemu plików.
 * Oznacza to, że encja możne zawierać się w katalogu.
 */
public interface FileSystemEntry {
    /**
     * Zwraca id na potrzeby systemu plikół.
     *
     * @return id na potrzeby systemu plików.
     */
    Integer getId();

    /**
     * Zwraca nazwę na potrzeby systemu plików.
     *
     * @return nazwa na potrzeby systemu plików.
     */
    String getName();
}
