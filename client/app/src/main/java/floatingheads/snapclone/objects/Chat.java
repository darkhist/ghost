package floatingheads.snapclone.objects;

/**
 * Created by QuinnSalas on 3/28/18.
 */

public class Chat {

    private String ID;
    private String message;
    private String name;


    public Chat() {
        // Constructor
    }

    public String getID() {
        return ID;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setMessage(String message) {
       this.message = message;
    }

    public void setName(String name) {
        this.name = name;
    }
}
