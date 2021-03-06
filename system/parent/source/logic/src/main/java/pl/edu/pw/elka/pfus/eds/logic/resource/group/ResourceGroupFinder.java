package pl.edu.pw.elka.pfus.eds.logic.resource.group;

import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.resource.group.dto.ResourceGroupWithAssignedUsers;
import pl.edu.pw.elka.pfus.eds.domain.dao.dto.SharedResourceGroupDto;
import pl.edu.pw.elka.pfus.eds.security.dto.RolesGrantedDto;

import java.util.List;

/**
 * Fasada operacji pobrania grup zasobów.
 */
public interface ResourceGroupFinder {
    /**
     * Zwraca listę grup zasobów aktualnie zalogowanego użytkownika.
     *
     * @return lista grup zasobów użytkownika.
     */
    List<ResourceGroup> getCurrentUserResourceGroups();

    /**
     * Zwraca listę grup zasobów wybranego użytkownika.
     *
     * @param user użytkownik.
     * @return lista grup zasobów użytkownika.
     */
    List<ResourceGroup> getUserResourceGroup(User user);

    /**
     * Zwraca listę grup zasobów wybranego użytkownika.
     *
     * @param userId id użytkownika.
     * @return lista grup zasobów użytkownika.
     */
    List<ResourceGroup> getUserResourceGroup(int userId);

    /**
     * Zwraca grupę zasobów o podanej nazwie
     * oraz listę użytkowników, którzy mają jakiekolwiek uprawnienia.
     *
     * @param name nazwa grupy zasobów.
     * @return grupa zasobów o podanej nazwie.
     */
    ResourceGroupWithAssignedUsers getByNameWithUsers(String name);

    /**
     * Zwraca listę grup, w których zalogowany użytkownik ma prawo udostępniać.
     * Listy są w postaci DTO, zawierające nazwę grupy i informację
     * czy dany dokument jest już udostępniony.
     *
     * @param documentId id dokumentu.
     * @return grupy, gdzie użytkownik może udostępniać.
     */
    List<SharedResourceGroupDto> getSharableGroupsForCurrentUserAndDocument(int documentId);

    /**
     * Zwraca listę grup, w których zalogowany użytkownik ma prawo udostępniać.
     * Listy są w postaci DTO, zawierające nazwę grupy i informację
     * czy dany katalog jest już udostępniony.
     *
     * @param directoryId id katalogu.
     * @return grupy, gdzie użytkownik może udostępniać.
     */
    List<SharedResourceGroupDto> getSharableGroupsForCurrentUserAndDirectory(int directoryId);

    /**
     * Zwraca listę ról dostępnych dla danej grupy zasobów.
     *
     * @param groupName nazwa grupy zasobów
     * @return lista ról.
     */
    List<RolesGrantedDto> getAvailableRolesForGroup(String groupName);

    /**
     * Zwraca listę posiadanych rol przez danego użytkownika na danej grupie zasobów.
     *
     * @param userName nazwa użytkownika.
     * @param resourceGroupName nazwa grupy zasobów.
     * @return lista ról.
     */
    List<RolesGrantedDto> getUserRolesOverResourceGroups(String userName, String resourceGroupName);

    /**
     * Zwraca listę grup, gdzie użytkownik ma jakieś uprawnienia.
     *
     * @return lista grup użytkownika.
     */
    List<ResourceGroup> getGroupsWhereCurrentUserHasAnyPrivilege();
}
