package pl.edu.pw.elka.pfus.eds.logic.comment;

import pl.edu.pw.elka.pfus.eds.domain.entity.Comment;

import java.util.List;

/**
 * Interfejs służący do wyszukiwania komentarzy.
 */
public interface CommentFinder {
    /**
     * Wyszukuje wszystkie komentarze dla danego dokumentu.
     *
     * @param documentId id dokumentu.
     * @return komentarze do dokumentu.
     */
    List<Comment> getCommentsForDocument(int documentId);
}
