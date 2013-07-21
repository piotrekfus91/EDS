package pl.edu.pw.elka.pfus.eds.util.message;

import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Fabryka wiadomości.
 */
public class MessageFactory {
    private static final Logger logger = Logger.getLogger(MessageFactory.class);

    private MessageFactory() {

    }

    /**
     * Zwraca nową wiadomość na podstawie jej parametrów.
     * Wiadomość automatycznie jest uzupełniania o bieżącą datę.
     *
     * @param type typ wiadomości.
     * @param text tekst wiadomości.
     * @return wiadomość.
     */
    public static Message getMessage(MessageType type, String text) {
        Message message = new MessageImpl(type, text, new Date());
        logger.info("message created: " + message);
        return message;
    }
}
