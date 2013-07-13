package pl.edu.pw.elka.pfus.eds.util.message;

import java.util.Date;

public class MessageImpl implements Message {
    private MessageType type;
    private String text;
    private Date date;

    MessageImpl(MessageType type, String text, Date date) {
        this.type = type;
        this.text = text;
        this.date = date;
    }

    public MessageType getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }
}
