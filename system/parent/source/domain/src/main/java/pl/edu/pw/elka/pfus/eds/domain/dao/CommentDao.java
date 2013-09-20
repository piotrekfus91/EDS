package pl.edu.pw.elka.pfus.eds.domain.dao;

import pl.edu.pw.elka.pfus.eds.domain.entity.Comment;

import java.util.List;

/**
 * DAO dla obiektów {@link Comment}.
 */
public interface CommentDao extends IdentifableDao<Comment> {
    /**
     * Zwraca listę komentarzy do dokumentu.
     *
     * @param documentId id dokumentu.
     * @return komentarze do dokumentu.
     */
    List<Comment> getCommentsForDocument(int documentId);
}
