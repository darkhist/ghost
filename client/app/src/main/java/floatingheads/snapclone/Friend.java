package floatingheads.snapclone;

import android.graphics.Bitmap;

/**
 * Created by root on 2/23/18.
 */

public class Friend {

    // will incorporate avatar image soon hopefully

    private int userID;
    private String userFirstName;
    private String userLastName;
    private String lastMessage;
    private Bitmap avatar;

    public Friend() {
        userID = -1;
        userFirstName = "Sample";
        userLastName = "Friend";
        lastMessage = "sample message";
        avatar = null;
    }

    public Friend(int userID, String userFirstName, String userLastName, String lastMessage) {
        this.userID = userID;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.lastMessage = lastMessage;
        avatar = null;
    }

    public Friend(int userID, String userFirstName, String userLastName, String lastMessage, Bitmap avatar) {
        this(userID, userFirstName, userLastName, lastMessage);
        this.avatar = avatar;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public void setUserLastName(String userLastName) {
        this.userFirstName = userLastName;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public int getUserID() {
        return userID;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public Bitmap getAvatar() {
        return avatar;
    }
}
