package backend;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: miguel
 * Date: 5/5/13
 * Time: 10:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class Inbox {
    String owner;
    List<Message> messages;

    public Inbox(String owner) {
        this.owner = owner;
        messages = new LinkedList<>();
    }

    public void post(Message message) {
        messages.add(message);
    }

    public Message read() {
        if(messages.size() == 0)
            return null;

        Message m = messages.get(0);
        messages.remove(0);

        return m;
    }
}
