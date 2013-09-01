package pl.edu.pw.elka.pfus.eds.security.resource.group;

import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;

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
     * Zwraca listę grup, których założycielem jest zalogowany użytkownik.
     *
     * @return lista grup użytkownika.
     */
    List<ResourceGroup> getGroupsFoundedByCurrentUser();

    /**
     * Zwraca listę grup, których założycielem jest użytkownik.
     *
     * @param user właściciel grup.
     * @return lista grup użytkownika.
     */
    List<ResourceGroup> getGroupsFoundedByUser(User user);

    /**
     * Zwraca listę grup, których założycielem jest użytkownik.
     *
     * @param login właściciel grup.
     * @return lista grup użytkownika.
     */
    List<ResourceGroup> getGroupsFoundedByUser(String login);
}
