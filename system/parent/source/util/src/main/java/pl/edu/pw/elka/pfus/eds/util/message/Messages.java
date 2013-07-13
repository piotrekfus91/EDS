package pl.edu.pw.elka.pfus.eds.util.message;

import java.util.Collection;

/**
 * Kolekcja przechowująca wiadomości.
 */
public interface Messages<T extends Collection<Message>> extends Iterable<Message> {
    /**
     * Wstawia nową wiadomość do kolekcji na podstawie parametrów.
     *
     * @param type typ wiadomości.
     * @param text tekst wiadomości.
     */
    void postMessage(MessageType type, String text);

    /**
     * Wstawia nową wiadomość do kolekcji.
     *
     * @param message wiadomość.
     */
    void postMessage(Message message);

    /**
     * Czyści kolekcję.
     */
    void clear();

    /**
     * Sprawdza czy kolekcja jest pusta.
     *
     * @return true jeśli kolekcja jest pusta, w przeciwnym wypadku false.
     */
    boolean isEmpty();

    /**
     * Zwraca ilość wiadomości w kolekcji.
     *
     * @return ilość wiadomości w kolekcji.
     */
    int size();

    /**
     * Zwraca wewnętrzną kolekcję.
     * Potrzebne dla velocity - może się iterować tylko po listach lub tablicach.
     *
     * @return wewnętrzna kolekcja.
     */
    T getCollection();
}
