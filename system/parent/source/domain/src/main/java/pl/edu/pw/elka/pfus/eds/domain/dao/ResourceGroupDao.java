package pl.edu.pw.elka.pfus.eds.domain.dao;

import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;

import java.util.List;

/**
 * DAO dla obiektów {@link ResourceGroup}.
 */
public interface ResourceGroupDao extends NamedDao<ResourceGroup>, IdentifableDao<ResourceGroup> {
    /**
     * Zwraca listę grup zasobów, których założycielem
     * jest podany użytkownik.
     *
     * @param user użytkownik.
     * @return lista grup zasobów.
     */
    List<ResourceGroup> getAllOfFounder(User user);

    /**
     * Zwraca listę grup zasobów, których założycielem
     * jest podany użytkownik.
     *
     * @param userId id użytkownika.
     * @return lista grup zasobów.
     */
    List<ResourceGroup> getAllOfFounder(int userId);

    /**
     * Zwraca grupy zasobów, których nazwa znajduje się w kolekcji.
     *
     * @param groupNames nazwy grup zasobów.
     * @return grupy zasobów.
     */
    List<ResourceGroup> getResourceGroupsWithNames(List<String> groupNames);
}
