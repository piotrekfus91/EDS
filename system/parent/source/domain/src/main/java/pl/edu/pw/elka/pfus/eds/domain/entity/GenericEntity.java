package pl.edu.pw.elka.pfus.eds.domain.entity;

import java.util.Collection;
import java.util.Collections;

/**
 * Abstrakcyjna klasa bazowa dla wszystkich encji bazodanowych.
 */
public abstract class GenericEntity implements Versionable {
    /**
     * Zleca obiektowi odpięcie się od kolekcji.
     */
    public abstract void removeFromAssociations();

    /**
     * Usuwa z kolekcji niepotrzebne alokacje na nulle.
     *
     * @param collection kolekcja do przeczyszczenia.
     */
    protected void cleanCollectionsFromNulls(Collection<? extends GenericEntity> collection) {
        collection.removeAll(Collections.singleton(null));
    }
}