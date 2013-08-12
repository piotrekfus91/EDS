package pl.edu.pw.elka.pfus.eds.util.file.system;

/**
 * Interfejs pozwalający na tworzenie plików w systemie
 * plików zgodnym z EDS.
 */
public interface FileCreator {
    /**
     * Tworzy plik w odpowiednim katalogu na podstawie.
     *
     * @param input tablica bajtów.
     * @param fileName nazwa pliku.
     * @return {@code true} jeśli operacja zakończyła się sukcesem,
     *                  {@code false} w przeciwnym razie.
     */
    void create(byte[] input, String fileName);
}
