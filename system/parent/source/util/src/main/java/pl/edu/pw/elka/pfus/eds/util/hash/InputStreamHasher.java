package pl.edu.pw.elka.pfus.eds.util.hash;

import java.io.InputStream;

/**
 * Interfejs dla obliczania skrótów z {@link InputStream}.
 */
public interface InputStreamHasher {
    /**
     * Zwraca hash strumienia w postaci {@code String}.
     *
     * @param inputStream strumień wejściowy.
     * @return hash strumienia.
     */
    String getString(InputStream inputStream);
}
