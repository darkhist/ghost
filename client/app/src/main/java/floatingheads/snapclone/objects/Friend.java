package floatingheads.snapclone.objects;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

/**
 * Created by Mike on 2/23/18.
 */

public class Friend extends User implements Comparable<Friend> {

    // will incorporate avatar image soon hopefully

    public static final int STATUS_PENDING = 0, STATUS_ACCEPTED = 1, STATUS_REJECTED = 2, STATUS_BLOCKED = 3;
    private int status;


    public Friend() {
        super();
    }

    public Friend(int userID, String userFirstName, String userLastName, int status) {
        this();
        setId(userID);
        setFirstName(userFirstName);
        setLastName(userLastName);
        this.status = status;
        setAvatar();
    }

    public Friend(int userID, String userFirstName, String userLastName, int status, Bitmap avatar) {
        this(userID, userFirstName, userLastName, status);
        setAvatar();
    }

    public void toggleStatusPending() {

    }

    public void toggleStatusAccepted() {

    }

    public void toggleStatusRejected() {

    }

    public void toggleStatusBlocked() {

    }

    public boolean isStatusPending() {
        return status == STATUS_PENDING;
    }

    public boolean isStatusAccepted() {
        return status == STATUS_ACCEPTED;
    }

    public boolean isStatusRejected() {
        return status == STATUS_REJECTED;
    }

    public boolean isStatusBlocked() {
        return status == STATUS_BLOCKED;
    }

//    @Override
    public int compareTo(@NonNull Friend o) {

        String name1 = getFirstName() + getLastName();
        Friend f;

        if (o instanceof Friend) {
            f = (Friend) o;

            String name2 = f.getFirstName() + f.getLastName();

            if (name1.compareTo(name2) < 0)
                return -1;
            else if (name1.compareTo(name2) > 0)
                return 1;

        }

        return 0;
    }
}
