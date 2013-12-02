package pl.edu.pw.elka.pfus.eds.security.resource.group;

import org.objectledge.security.object.Group;
import org.objectledge.security.object.SecurityUser;
import pl.edu.pw.elka.pfus.eds.security.dto.RolesGrantedDto;

import java.util.List;

/**
 * Interfejs zarządzania grupami zasobów.
 */
public interface ResourceGroupManager {
    /**
     * Tworzy nową grupę zasobów.
     *
     * @param name nazwa grupy zasobów.
     */
    void createResourceGroup(String name);

    /**
     * Zmienia nazwę grupy zasobów.
     *
     * @param oldName stara nazwa.
     * @param newName nowa nazwa.
     */
    void renameResourceGroup(String oldName, String newName);

    /**
     * Usuwa grupę zasobów.
     *
     * @param name nazwa grupy.
     */
    void deleteResourceGroup(String name);

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
     * @return
     */
    List<RolesGrantedDto> getUserRolesOverResourceGroup(String userName, String resourceGroupName);

    /**
     * Ustawia użytkownikowi podane role na danej grupie zasobów.
     * Pozostałe role są odbierane.
     *
     * @param userName nazwa użytkownika.
     * @param resourceGroupName nazwa grupy zasobów.
     * @param roleNames lista nazw ról, które ma posiadać użytkownik.
     */
    void setUserRolesOnResourceGroup(String userName, String resourceGroupName, List<String> roleNames);

    /**
     * Zwraca listę użytkowników, którzy mają jakiekolwiek uprawnienia
     * w grupie zasobów o zadanej nazwie.
     *
     * @param resourceGroupName nazwa grupy zasobów.
     * @return użytkownicy.
     */
    List<SecurityUser> getAllUsersWithAnyPrivilegeOnResourceGroup(String resourceGroupName);

    /**
     * Zwraca listę grup, w których użytkownik ma jakiekolwiek uprawnienia.
     *
     * @param userName nazwa użytkownika.
     * @return lista grup, w których użytkownik ma uprawnienia.
     */
    List<Group> getGroupsWhereUserHasAnyPrivilege(String userName);

    /**
     * Zwraca listę grup, gdzie użytkownik ma dane uprawnienie.
     *
     * @param userName nazwa użytkownika.
     * @param privilegeName nazwa uprawnienia.
     * @return grupy, gdzie użytkownik ma dane uprawnienie.
     */
    List<Group> getGroupsWhereUserHasPrivilege(String userName, String privilegeName);
}
