package pl.edu.pw.elka.pfus.eds.domain.entity;

/**
 * Oznacza encję, jako element systemu plików.
 * Oznacza to, że encja możne zawierać się w katalogu.
 */
public interface FileSystemEntry {
    /**
     * Zwraca nazwę na potrzeby systemu plików.
     *
     * @return nazwa na potrzeby systemu plików.
     */
    String getName();
}
