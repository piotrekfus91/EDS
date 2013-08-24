package pl.edu.pw.elka.pfus.eds.security;

import pl.edu.pw.elka.pfus.eds.security.resource.group.ResourceGroupManager;
import pl.edu.pw.elka.pfus.eds.security.user.UserManager;

/**
 * Fasada dostępu do systemu bezpieczeństwa.
 */
public interface SecurityFacade extends UserManager, ResourceGroupManager {

}
