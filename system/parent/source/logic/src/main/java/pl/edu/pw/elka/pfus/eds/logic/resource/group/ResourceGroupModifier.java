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
}
