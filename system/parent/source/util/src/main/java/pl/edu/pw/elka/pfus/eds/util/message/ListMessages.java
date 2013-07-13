package pl.edu.pw.elka.pfus.eds.util.message;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ListMessages implements Messages<List<Message>> {
    private List<Message> messageList = new LinkedList<>();

    @Override
    public void postMessage(MessageType type, String text) {
        Message message = MessageFactory.getMessage(type, text);
        postMessage(message);
    }

    @Override
    public void postMessage(Message message) {
        messageList.add(message);
    }

    @Override
    public void clear() {
        messageList.clear();
    }

    @Override
    public boolean isEmpty() {
        return messageList.isEmpty();
    }

    @Override
    public int size() {
        return messageList.size();
    }

    @Override
    public List<Message> getCollection() {
        return messageList;
    }

    @Override
    public Iterator<Message> iterator() {
        return messageList.iterator();
    }
}
