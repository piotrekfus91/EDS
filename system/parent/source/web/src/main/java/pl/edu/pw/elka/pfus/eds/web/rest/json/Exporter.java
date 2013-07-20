package pl.edu.pw.elka.pfus.eds.web.rest.json;

/**
 * Interfejs odpowiedzialny za eksport zadanego obiektu do zadanego typu.
 *
 * @param <S> typ zwracany.
 * @param <T> typ zadany do eksportu.
 */
public interface Exporter<S, T> {
    /**
     * Dokonuje eksportu zadanego obiektu.
     *
     * @param object obiekt do eksportu.
     * @return wyeksportowany obiekt.
     */
    S export(T object);
}
