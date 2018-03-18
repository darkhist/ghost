package floatingheads.snapclone.objects;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

/**
 * Created by Mike on 2/23/18.
 */

public class Friend extends User implements Comparable<Friend> {

    // will incorporate avatar image soon hopefully


    public Friend() {
        super();
    }

    public Friend(int userID, String userFirstName, String userLastName) {
        this();
        setId(userID);
        setFirstName(userFirstName);
        setLastName(userLastName);
        setAvatar();
    }

    public Friend(int userID, String userFirstName, String userLastName, Bitmap avatar) {
        this(userID, userFirstName, userLastName);
        setAvatar();
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
