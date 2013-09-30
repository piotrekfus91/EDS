package pl.edu.pw.elka.pfus.eds.logic.search;

/**
 * Interfejs dostarczający API do zastępowania znaków narodowych
 * przez zadane odpowiedniki.
 */
public interface NationalCharacterReplacer {
    /**
     * Zamienia wszystkie wystąpienia specyficznych znaków
     * na inne.
     *
     * @param input dane wejściowe.
     * @return string z zamienionymi znakami.
     */
    String replaceAll(String input);
}
