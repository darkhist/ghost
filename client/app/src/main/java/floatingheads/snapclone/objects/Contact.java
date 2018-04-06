package floatingheads.snapclone.objects;

import android.graphics.Bitmap;

/**
 * Created by Mike on 2/24/18.
 */

public class Contact extends Friend {

    private int userID;
    private String userFirstName;
    private String userLastName;
    private String lastMessage;
    private Bitmap avatar;

    public Contact() {
        super();
    }

    public Contact(int userID, String userFirstName, String userLastName, String lastMessage) {
        super(userID, userFirstName,userLastName, Friend.STATUS_ACCEPTED);
        this.lastMessage = lastMessage;
        this.avatar = null;
    }

    public Contact(int userID, String userFirstName, String userLastName, String lastMessage, Bitmap avatar) {
        this(userID, userFirstName, userLastName, lastMessage);
        this.avatar = avatar;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastMessage() {
        return lastMessage;
    }

}
