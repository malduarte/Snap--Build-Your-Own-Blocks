package backend;

/**
 * Created with IntelliJ IDEA.
 * User: miguel
 * Date: 5/5/13
 * Time: 10:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class Message {
    private String from;
    private String to;
    private String message;

    public Message(String from, String to, String message) {
        this.from = from;
        this.to = to;
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }
}
