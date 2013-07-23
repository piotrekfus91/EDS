package pl.edu.pw.elka.pfus.eds.util.message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(date);
    }
}
