package backend;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * User: miguel
 */
public class SnapMsgHandler extends HttpServlet {
    static HashMap<String, Inbox> post;

    static {
        post = new HashMap<>();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        Inbox inbox =  post.get(name);
        if(inbox == null) {
              post.put(name, new Inbox(name));
        }

        String op = req.getParameter("op");
        switch (op) {
            case "msgto":
                sendMessage(name, req.getParameter("destination"), req.getParameter("message"), resp);
                break;
            case "read":
                readMessages(inbox, resp);
                break;
            default:
                sendReply(resp, HttpServletResponse.SC_BAD_REQUEST, "Unknown operation");

        }
    }

    private void readMessages(Inbox inbox, HttpServletResponse resp) throws IOException {
        Message message = inbox.read();
        if(message == null) {
            sendReply(resp, HttpServletResponse.SC_OK, "No messages");
        } else {
            sendReply(resp, HttpServletResponse.SC_OK, message.getFrom() + "," + message.getMessage());
        }
    }

    private void sendReply(HttpServletResponse resp, int status, String message) throws IOException {
        resp.setContentType("text/plain");
        resp.setStatus(status);
        resp.getWriter().println(message);
        resp.getWriter().flush();
    }

    private void sendMessage(String from, String msgto, String message, HttpServletResponse resp) throws IOException {
        Inbox inbox = post.get(msgto);
        if(inbox == null) { // Unknown destination
            sendReply(resp, HttpServletResponse.SC_BAD_REQUEST, "FAIL:Unkown destination!");
        } else if(message == null || message.isEmpty()) {
            sendReply(resp, HttpServletResponse.SC_BAD_REQUEST, "FAIL: No message attribute");
        } else {
                inbox.post(new Message(from, msgto, message));
                sendReply(resp, HttpServletResponse.SC_OK, "Message sent");
        }
    }
}
