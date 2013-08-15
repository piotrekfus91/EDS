package pl.edu.pw.elka.pfus.eds.domain.entity;

/**
 * Abstrakcyjna klasa bazowa dla wszystkich encji bazodanowych.
 */
public abstract class GenericEntity {
    /**
     * Zleca obiektowi odpięcie się od kolekcji.
     */
    public abstract void removeFromAssociations();
}