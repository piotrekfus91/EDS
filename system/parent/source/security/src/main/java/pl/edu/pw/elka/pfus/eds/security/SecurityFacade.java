package pl.edu.pw.elka.pfus.eds.security;

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
    public void createUser(String login, String firstName, String lastName, String password);
}
