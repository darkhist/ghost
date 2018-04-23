package floatingheads.snapclone.objects;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

/**
 * Created by Mike on 3/19/18.
 */

public class Profile extends ConstraintLayout {

    User user = null;

    public Profile(Context context, AttributeSet attr) {
        super(context,attr);
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

    }

}