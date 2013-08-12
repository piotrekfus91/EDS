package pl.edu.pw.elka.pfus.eds.util.hash;

import java.io.InputStream;

/**
 * Interfejs dla obliczania skrótów z {@link InputStream}.
 */
public interface InputStreamHasher extends Hasher<InputStream> {
    /**
     * Zwraca hash w postaci stringa.
     *
     * @param input dane wejściowe.
     * @return hash w postaci stringa.
     */
    String getString(InputStream input);

    /**
     * Zwraca długość danego hasha wyrażoną jako ilość znaków
     * w systemie szesnastkowym.
     *
     * @return długość hasha.
     */
    int getHashStringLength();

    /**
     * Zwraca ilość bajtów hashu.
     * @return
     */
    int getBytesNumber();
}
