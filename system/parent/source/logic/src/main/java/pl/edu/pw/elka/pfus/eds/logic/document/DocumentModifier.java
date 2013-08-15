package pl.edu.pw.elka.pfus.eds.logic.document;

/**
 * Interfejs do modyfikowania plików.
 */
public interface DocumentModifier {
    /**
     * Tworzy nowy plik na podstawie zadanej nazwy i tablicy bajtów.
     * Plik jest utworzony w odpowiednim katalogu (zgodnie z hashem),
     * jego nazwą jest data w postaci milisekund, zaś nazwa
     * trafia do bazy danych.
     *
     * @param name nazwa pliku.
     * @param input dane wejściowe.
     */
    void create(String name, byte[] input);

    /**
     * Zmienia nazwę podanego przez id dokumentu na podaną.
     *
     * @param documentId id dokumentu.
     * @param newName nowa nazwa.
     */
    void rename(int documentId, String  newName);
    /**
     * Przenosi plik do wskazanego za pomocą id katalogu.
     *
     * @param documentId id dokumentu.
     * @param destinationDirectoryId id katalogu docelowego.
     */
    void move(int documentId, int destinationDirectoryId);

    /**
     * Usuwa plik zadany na podstawie id.
     *
     * @param documentId id dokumentu.
     */
    void delete(int documentId);

    /**
     * Usuwa wszystkie pliki sesyjne.
     */
    void cleanSessionDocuments();
}
