package pl.edu.pw.elka.pfus.eds.security.user;

import org.objectledge.context.Context;
import org.objectledge.security.object.SecurityUser;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;

import javax.servlet.http.HttpServletRequest;

public interface UserManager {
    /**
     * Tworzy nowego użytkownika systemowego.
     *
     * @param login login użytkownika.
     * @param firstName imię.
     * @param lastName nazwisko.
     * @param password hasło.
     * @return stworzony użytkownik.
     */
    void createUser(String login, String firstName, String lastName, String password);

    /**
     * Sprawdza czy podane login i hasło pasują do istniejącego użytkownika z bazy.
     *
     * @param context bieżący context.
     * @param login login użytkownika.
     * @param password podane hasło.
     * @return obiekt typu {@link pl.edu.pw.elka.pfus.eds.domain.entity.User} pasujący do podanych loginu i hasła.
     */
    User logIn(Context context, String login, String password);

    /**
     * Zwraca zalogowanego użytkownika.
     *
     * @param context bieżący context.
     * @return bieżący użytkownik, jeśli istnieje, w przeciwnym wypadku null.
     */
    User getCurrentUser(Context context);

    /**
     * Zwraca zalogowanego użytkownika.
     *
     * @param request request HTTP.
     * @return bieżący użytkownik, jeśli istnieje, w przeciwnym wypadku null.
     */
    User getCurrentUser(HttpServletRequest request);

    /**
     * Zwraca aktualnego użytkownika systemu bezpieczeństwa.
     *
     * @param context bieżący context.
     * @return użytkownik systemu bezpieczeńśtwa jeśli istnieje, w przeciwnym wypadku null.
     */
    SecurityUser getCurrentSecurityUser(Context context);

    /**
     * Zwraca informację, czy jest zalogowanu użytkownik w bieżącej sesji.
     *
     * @param context bieżący context.
     * @return true jeśli jest użytkownik zalogowany, w przeciwnym razie false.
     */
    boolean isLogged(Context context);
}
