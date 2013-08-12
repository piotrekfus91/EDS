package pl.edu.pw.elka.pfus.eds.util.hash;

/**
 * Interfejs generyczny dla hashowania.
 *
 * @param <T> typ wejściowy.
 */
public interface Hasher<T> {
    /**
     * Zwraca hash w postaci stringa.
     *
     * @param input dane wejściowe.
     * @return hash w postaci stringa.
     */
    String getString(T input);

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
