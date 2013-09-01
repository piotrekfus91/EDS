package pl.edu.pw.elka.pfus.eds.logic.resource.group;

import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.resource.group.dto.ResourceGroupWithAssignedUsers;
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
     * Zwraca listę posiadanych rol przez danego użytkownika na danej grupie zasobów.
     *
     * @param userName nazwa użytkownika.
     * @param resourceGroupName nazwa grupy zasobów.
     * @return
     */
    List<RolesGrantedDto> getUserRolesOverResourceGroups(String userName, String resourceGroupName);
}
