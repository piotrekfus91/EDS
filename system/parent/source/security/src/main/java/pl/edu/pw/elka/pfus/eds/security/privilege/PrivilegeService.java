package pl.edu.pw.elka.pfus.eds.security.privilege;

import java.util.Map;

/**
 * Interfejs udostępniający możliwość sprawdzania ról.
 */
public interface PrivilegeService {
    /**
     * Zwraca informację, czy użytkownik ma uprawnienie o danej nazwie na danej grupie.
     *
     * @param userName nazwa użytkownika.
     * @param privilegeName nazwa uprawnienia.
     * @param groupName nazwa grupy.
     * @return {@code true} jeśli użytkownik ma uprawnienie, {@code false} w przeciwnym razie.
     */
    boolean hasPrivilege(String userName, String privilegeName, String groupName);

    /**
     * Zwraca mapę, zawierającą pary: nazwa uprawnienia - posiadanie.
     *
     * @param userName nazwa użytkownika.
     * @param groupName nazwa grupy.
     * @return
     */
    Map<String, Boolean> getPrivilegesStatus(String userName, String groupName);
}
