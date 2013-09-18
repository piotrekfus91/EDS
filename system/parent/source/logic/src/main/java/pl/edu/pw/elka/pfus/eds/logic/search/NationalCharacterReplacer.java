package pl.edu.pw.elka.pfus.eds.logic.search;

/**
 * Interfejs dostarczający API do zastępowania znaków narodowych
 * przez zadane odpowiedniki.
 */
public interface NationalCharacterReplacer {
    String replaceAll(String input);
}
