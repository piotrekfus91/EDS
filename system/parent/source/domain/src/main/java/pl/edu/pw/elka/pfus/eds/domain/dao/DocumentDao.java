package pl.edu.pw.elka.pfus.eds.domain.dao;

import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;

import java.util.List;

/**
 * DAO dla obiektów {@link Document}.
 */
public interface DocumentDao extends IdentifableDao<Document> {
    /**
     * Zwraca listę dokumentów sesyjnych, czyli takich, które nie mają
     * jeszcze katalogu.
     *
     * @param user właściciel.
     * @return lista dokumentów sesyjnych.
     */
    List<Document> getSessionDocuments(User user);

    /**
     * Zwraca listę dokumentów sesyjnych, czyli takich, które nie mają
     * jeszcze katalogu.
     *
     * @param userId id właściciela.
     * @return lista dokumentów sesyjnych.
     */
    List<Document> getSessionDocuments(int userId);

    /**
     * Zwraca listę dokumentów wraz z grupami, do których należy.
     *
     *
     * @param documentId id dokumentu.
     * @return dokument.
     */
    Document getDocumentWithGroups(int documentId);

    /**
     * Usuwa pliki sesyjne podanego użytkownika.
     *
     * @param user użytkownik.
     */
    void deleteSessionDocuments(User user);

    /**
     * Usuwa pliki sesyjne podanego użytkownika.
     *
     * @param userId id użytkownika.
     */
    void cleanSessionDocuments(int userId);
}
