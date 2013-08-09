package pl.edu.pw.elka.pfus.eds.util.config;

import java.util.Date;

/**
 * Interfejs dostarczający konfigurację typu key-value.
 */
public interface Config {
    /**
     * Określa format daty akceptowany przez config.
     */
    String DATE_FORMAT = "dd-MM-yyyy";

    /**
     * Zwraca wartość typu {@code String} z konfiguracji
     * na podstawie klucza.
     *
     * @param key klucz.
     * @return wartość typu {@code String}.
     * @throws ConfigException gdy klucz nie został odnaleziony.
     */
    String getString(String key) throws ConfigException;

    /**
     * Zwraca wartość typu {@code int} z konfiguracji
     * na podstawie klucza.
     *
     * @param key klucz.
     * @return wartość typu {@code int}.
     * @throws ConfigException gdy klucz nie został odnaleziony.
     */
    int getInt(String key) throws ConfigException;

    /**
     * Zwraca wartość typu {@code Date} z konfiguracji
     * na podstawie klucza.
     *
     * @param key klucz.
     * @return wartość typu {@code Date}.
     * @throws ConfigException gdy klucz nie został odnaleziony.
     */
    Date getDate(String key) throws ConfigException;
}
