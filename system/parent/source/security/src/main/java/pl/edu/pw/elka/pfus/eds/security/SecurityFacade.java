package pl.edu.pw.elka.pfus.eds.security;

import pl.edu.pw.elka.pfus.eds.domain.entity.User;

/**
 * Fasada dostępu do systemu bezpieczeństwa.
 */
public interface SecurityFacade {
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
     * @param login login użytkownika.
     * @param password podane hasło.
     * @return obiekt typu {@link User} pasujący do podanych loginu i hasła.
     */
    User logIn(String login, String password);
}
