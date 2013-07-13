package pl.edu.pw.elka.pfus.eds.util.message;

import java.util.Date;

/**
 * Wiadomość przekazywana z warstwy logiki do warstwy prezentacji.
 */
public interface Message {
    /**
     * Zwraca typ wiadomości.
     *
     * @return typ wiadomości.
     */
    MessageType getType();

    /**
     * Zwraca tekst do wyświetlenia w wiadomości.
     *
     * @return teskt do wyświetlenia.
     */
    String getText();

    /**
     * Zwraca czas nadania wiadomości.
     *
     * @return czas nadania wiadomości.
     */
    Date getDate();
}
