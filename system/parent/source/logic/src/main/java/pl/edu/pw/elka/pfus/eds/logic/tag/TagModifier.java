package pl.edu.pw.elka.pfus.eds.logic.tag;

/**
 * Interfejs do zarządzania tagami.
 */
public interface TagModifier {
    /**
     * Podaną listę tagów dzieli na pojedyncze tagi i dodaje do dokumentu.
     *
     * @param documentId id dokumentu.
     * @param commaSeparatedTagList lista tagów oddzielonych przecinkiem.
     */
    void addTagsToDocument(int documentId, String commaSeparatedTagList);
}
