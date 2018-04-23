package floatingheads.snapclone.objects;

/**
 * Created by QuinnSalas on 3/28/18.
 */

public class Message {

    private String ID;
    private String message;
    private String user;
    private long timestamp;

    public Message() {
        // Constructor
    }

    public String getID() {
        return ID;
    }

    public String getMessage() {
        return message;
    }

    public String getUser() {
        return user;
    }

    public long getTimestamp() {return timestamp; }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setMessage(String message) {
       this.message = message;
    }

    public void setUser(String user) { this.user = user; }

    public void setTimestamp(long timestamp) {this.timestamp = timestamp;}
}
