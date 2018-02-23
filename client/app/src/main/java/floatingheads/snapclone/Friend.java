package floatingheads.snapclone;

/**
 * Created by root on 2/23/18.
 */

public class Friend {

    // will incorporate avatar image soon hopefully

    private int userID;
    private String userFirstName;
    private String userLastName;
    private String lastMessage;

    public Friend() {
        userID = -1;
        userFirstName = "Sample";
        userLastName = "Friend";
        lastMessage = "sample message";
    }

    public Friend(int userID, String userFirstName, String userLastName, String lastMessage) {
        this.userID = userID;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.lastMessage = lastMessage;
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
}
