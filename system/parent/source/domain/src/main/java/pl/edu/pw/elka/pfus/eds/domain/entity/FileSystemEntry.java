package pl.edu.pw.elka.pfus.eds.domain.entity;

/**
 * Oznacza encję, jako element systemu plików.
 * Oznacza to, że encja możne zawierać się w katalogu.
 */
public interface FileSystemEntry {
    /**
     * Separator dla ścieżek, używany w metodzie {@see getStringPath()}.
     */
    String PATH_SEPARATOR = "/";

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

    /**
     * Zwraca ścieżkę w postaci stringa z separatorem {@see PATH_SEPARATOR}.
     * Przykładowa ścieżka: /parent/firstChild/thisDirectory.
     *
     * @return ścieżka w postaci string.
     */
    String getStringPath();
}
