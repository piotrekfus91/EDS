package pl.edu.pw.elka.pfus.eds.util.file.system;

import java.io.File;
import java.io.InputStream;

/**
 * Interfejs pozwalający na tworzenie plików w systemie
 * plików zgodnym z EDS.
 */
public interface FileManager {
    /**
     * Tworzy plik w odpowiednim katalogu na podstawie.
     *
     *
     * @param input tablica bajtów.
     * @param fileName nazwa pliku.
     * @return {@code true} jeśli operacja zakończyła się sukcesem,
     *                  {@code false} w przeciwnym razie.
     */
    File create(byte[] input, String fileName);

    /**
     * Zwraca plik w postaci tablicy bajtów.
     *
     * @param fileName nazwa pliku.
     * @param hash hash pliku.
     * @return tablica bajtów zawartości pliku.
     */
    byte[] getAsByteArray(String fileName, String hash);

    /**
     * Zwraca plik w postaci strumienia.
     *
     * @param fileName nazwa pliku.
     * @param hash hash pliku.
     * @return strumień z zawartości pliku.
     */
    InputStream getAsStream(String fileName, String hash);

    /**
     * Usuwa plik o podanej nazwie z podanej za pomocą skrótu ścieżĸi.
     *
     * @param name nazwa pliku.
     * @param hash hash zawartości pliku.
     */
    void delete(String name, String hash);
}
