package pl.edu.pw.elka.pfus.eds.logic.document;

/**
 * Interfejs do modyfikowania plików.
 */
public interface DocumentModifier {
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
}
