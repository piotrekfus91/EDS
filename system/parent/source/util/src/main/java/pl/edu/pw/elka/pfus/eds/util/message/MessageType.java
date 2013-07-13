package pl.edu.pw.elka.pfus.eds.util.message;

/**
 * Typ wiadomości, patrz {@link Message}.
 *
 * Typy wiadomości są mocno powiązane z używaną biblioteką,
 * w tym przypadku jest to noty.
 * http://needim.github.io/noty/
 */
public enum MessageType {
    /**
     * Zwykła wiadomość.
     */
    ALERT("alert"),

    /**
     * Operacja wykonana poprawnie.
     */
    SUCCESS("success"),

    /**
     * Operacja wykonana z błędem.
     */
    ERROR("error"),

    /**
     * Operacja powiodła się, ale wystąpiły pewne trudności.
     */
    WARNING("warning"),

    /**
     * Informacja.
     */
    INFO("information");

    private String toString;

    private MessageType(String toString) {
        this.toString = toString;
    }

    @Override
    public String toString() {
        return toString;
    }
}
