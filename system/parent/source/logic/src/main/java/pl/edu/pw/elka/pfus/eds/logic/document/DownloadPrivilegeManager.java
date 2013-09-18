package pl.edu.pw.elka.pfus.eds.logic.document;

import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;

import java.util.List;

/**
 * Wsparcie dla informacji o downloadzie dokumentów.
 */
public interface DownloadPrivilegeManager {
    /**
     * Zwraca informację, czy użytkownik może sciągąć dokument.
     *
     * @param user użytkownik.
     * @param document dokument.
     * @return czy użytkownik może pobrać dokument.
     */
    boolean canDownload(User user, Document document);

    /**
     * Zwraca informację, czy użytkownik może sciągąć dokument.
     *
     * @param user użytkownik.
     * @param documentId id dokumentu.
     * @return czy użytkownik może pobrać dokument.
     */
    boolean canDownload(User user, int documentId);

    /**
     * Filtruje dokumenty na podstawie dostępności dla danego użytkownika.
     * Zwraca listę tylko tych dostępnych.
     *
     * @param user użytkownik.
     * @param documents lista dokumentów.
     * @return
     */
    List<Document> filterOutInaccessibleDocuments(User user, List<Document> documents);
}
