package pl.edu.pw.elka.pfus.eds.web.rest.json;

/**
 * Interfejs powstały raczej w celach "ładnościowych", bo JSON to po prostu string.
 *
 * @param <T> typ exportowany.
 */
public interface JsonExporter<T>  {
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
     * @return
     */
    String  exportFailure(String errorMessage, T object);
}
