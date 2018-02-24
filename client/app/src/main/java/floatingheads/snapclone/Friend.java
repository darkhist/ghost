package floatingheads.snapclone;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

/**
 * Created by root on 2/23/18.
 */

public class Friend implements Comparable {

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
        lastMessage = "";
        avatar = null;
    }

    public Friend(int userID, String userFirstName, String userLastName) {
        this.userID = userID;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.lastMessage = "";
        avatar = null;
    }

    public Friend(int userID, String userFirstName, String userLastName, String lastMessage) {
        this(userID, userFirstName, userLastName);
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

    @Override
    public int compareTo(@NonNull Object o) {

        Friend f = (Friend) o;

        String name1 = getUserFirstName() + getUserLastName();
        String name2 = f.getUserFirstName() + f.getUserLastName();

        if (name1.compareTo(name2) < 0)
            return -1;
        else if (name1.compareTo(name2) > 0)
            return 1;

        return 0;
    }
}
