package pl.edu.pw.elka.pfus.eds.domain.dao;

import pl.edu.pw.elka.pfus.eds.domain.entity.MimeType;

/**
 * DAO dla obiektów typu {@link MimeType}.
 */
public interface MimeTypeDao extends IdentifableDao<MimeType>  {
    /**
     * Wyszukuje typ MIME na podstawie jego... typu:)
     *
     * @param type typ.
     * @return mime type.
     */
    MimeType findByType(String type);
}
