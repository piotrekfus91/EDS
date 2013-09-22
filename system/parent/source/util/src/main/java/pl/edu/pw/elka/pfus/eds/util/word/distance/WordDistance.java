package pl.edu.pw.elka.pfus.eds.util.word.distance;

/**
 * Interfejs dla obliczania odległości między dwoma słowami.
 */
public interface WordDistance {
    /**
     * Wyznacza odległość między dwoma stringami.
     *
     * @param str1 pierwszy string.
     * @param str2 drugi string.
     * @return odległość między dwoma stringami.
     */
    int distance(String str1, String str2);
}
