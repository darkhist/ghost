package floatingheads.snapclone.objects;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

/**
 * Created by Mike on 2/23/18.
 */

public class Friend extends User implements Comparable<Friend> {

    // will incorporate avatar image soon hopefully

    private int userID;
    private String userFirstName;
    private String userLastName;
    private Bitmap avatar;

    public Friend() {
        super();
    }

    public Friend(int userID, String userFirstName, String userLastName) {
        this();
        this.userID = userID;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        avatar = null;
    }

    public Friend(int userID, String userFirstName, String userLastName, Bitmap avatar) {
        this(userID, userFirstName, userLastName);
        this.avatar = avatar;
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
