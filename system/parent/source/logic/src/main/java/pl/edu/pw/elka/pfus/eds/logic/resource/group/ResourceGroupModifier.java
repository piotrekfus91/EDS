package pl.edu.pw.elka.pfus.eds.logic.resource.group;

import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;

/**
 * Fasada dla operacji modyfikacji encji {@link pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup}.
 */
public interface ResourceGroupModifier {
    /**
     * Tworzy nową grupę zasobów.
     *
     * @param name nazwa grupy zasobów.
     * @param description opis grupy.
     * @return utworzona grupa.
     */
    ResourceGroup create(String name, String description);

    /**
     * Aktualizuje nazwę i opis grupy zasobów.
     *
     * @param oldName stara nazwa.
     * @param newName nowa nazwa.
     * @param description nowy opis.
     * @return zaktualizowana grupa zasobów.
     */
    ResourceGroup updateNameAndDescription(String oldName, String newName, String description);

    /**
     * Usuwa grupę zasobów.
     *
     * @param name nazwa grupy zasobów do usunięcia.
     */
    void delete(String name);
}
