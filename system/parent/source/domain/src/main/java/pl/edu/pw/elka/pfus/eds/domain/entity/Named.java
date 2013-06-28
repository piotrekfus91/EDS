package pl.edu.pw.elka.pfus.eds.domain.entity;

/**
 * Interfejs oznaczający, że da się wyszukiwać obiekt jednoznacznie na podstawie nazwy.
 * Jest to równoznaczne z tym, że nazwa powinna być unikalna.
 */
public interface Named {

    /**
     * Zwraca nazwę obiektu.
     * 
     * @return nazwa obiektu.
     */
    public String getName();
}