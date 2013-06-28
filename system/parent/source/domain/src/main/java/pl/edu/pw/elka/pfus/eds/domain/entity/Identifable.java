package pl.edu.pw.elka.pfus.eds.domain.entity;

/**
 * Interfejs oznaczający, że dana klasa jest identyfikowalna.
 * Sprowadza się to do faktu, że kluczem głównym w bazie danych jest int.
 */
public interface Identifable {

    /**
     * Zwraca id encji.
     * 
     * @return id encji.
     */
    public Integer getId();
}