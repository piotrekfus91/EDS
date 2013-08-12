package pl.edu.pw.elka.pfus.eds.util.hash;

/**
 * Interfejs {@link Hasher} dla tablic bajtów.
 */
public interface ByteArrayHasher extends Hasher<byte[]> {
    /**
     * Zwraca hash w postaci stringa.
     *
     * @param input dane wejściowe.
     * @return hash w postaci stringa.
     */
    String getString(byte[] input);

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
