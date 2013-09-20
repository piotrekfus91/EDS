package pl.edu.pw.elka.pfus.eds.logic.comment;

/**
 * Interfejs zarządzania komentarzami.
 */
public interface CommentModifier {
    /**
     * Dodaje nowy komentarz do dokumentu.
     *
     * @param documentId id dokumentu.
     * @param content treść komentarza.
     */
    void addComment(int documentId, String content);
}
