package pl.edu.pw.elka.pfus.eds.domain.entity;

/**
 * Interfejs oznaczający, że dana klasa implementuje optymistyczne blokowanie.
 */
public interface Versionable {

    /**
     * Zwraca wersję encji na potrzeby optymistycznego blokowania.
     * 
     * @return wersja.
     */
    public Integer getVersion();
}