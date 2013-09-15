package pl.edu.pw.elka.pfus.eds.web.rest.json;

import pl.edu.pw.elka.pfus.eds.logic.exception.LogicException;

/**
 * Interfejs powstały raczej w celach "ładnościowych", bo JSON to po prostu string.
 *
 * @param <T> typ exportowany.
 */
public interface SuccessFailureJsonExporter<T>  {
    /**
     * Dokonuje eksportu zadanego obiektu jeśli operacje poprzedzające
     * zakończyły się sukcesem.
     *
     * @param object obiekt do eksportu.
     * @return wyeksportowany obiekt.
     */
    String exportSuccess(T object);

    /**
     * Dokonuje eksportu obiektu, jeśli operacje poprzedzające
     * zakończyły się błędem.
     *
     * @param errorMessage informacja o błędzie.
     * @param object
     * @return wyeksportowany błąd.
     */
    String exportFailure(String errorMessage, T object);

    /**
     * Dokonuje eksportu wyjątku logiki.
     *
     * @param e
     * @return wyeksportowany wyjątek.
     */
    String exportFailure(LogicException e);
}
