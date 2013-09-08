package pl.edu.pw.elka.pfus.eds.logic.resource.group;

import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;
import pl.edu.pw.elka.pfus.eds.security.dto.RolesGrantedDto;

import java.util.List;
import java.util.Map;

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
     * Aktualizuje role użytkownika na grupie zasobów.
     *
     * @param groupName nazwa grupy.
     * @param userName nazwa użytkownika.
     * @param rolesGranted lista ról.
     */
    void updateRoles(String groupName, String userName, List<RolesGrantedDto> rolesGranted);

    /**
     * Aktualizuje informacje o publikowaniu danego dokumentu.
     *
     * @param documentId id dokumentu.
     * @param sharedInGroups mapa zawierające pary: nazwa grupy zasobów - czy dostępny.
     */
    void updateDocumentPublishing(int documentId, Map<String, Boolean> sharedInGroups);

    /**
     * Aktualizuje informacje o publikowaniu danego katalogu.
     *
     * @param directoryId id katalogu.
     * @param sharedInGroups mapa zawierające pary: nazwa grupy zasobów - czy dostępny.
     */
    void updateDirectoryPublishing(int directoryId, Map<String, Boolean> sharedInGroups);

    /**
     * Usuwa grupę zasobów.
     *
     * @param name nazwa grupy zasobów do usunięcia.
     */
    void delete(String name);
}
