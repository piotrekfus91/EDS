package pl.edu.pw.elka.pfus.eds.security.resource.group;

import org.objectledge.security.object.SecurityUser;

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

    void renameResourceGroup(String oldName, String newName);

    /**
     * Usuwa grupę zasobów.
     *
     * @param name nazwa grupy.
     */
    void deleteResourceGroup(String name);

    /**
     * Zwraca listę użytkowników, którzy mają jakiekolwiek uprawnienia
     * w grupie zasobów o zadanej nazwie.
     *
     * @param resourceGroupName nazwa grupy zasobów.
     * @return użytkownicy.
     */
    List<SecurityUser> getAllUsersWithAnyPrivilegeOnResourceGroup(String resourceGroupName);
}
