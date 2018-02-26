package floatingheads.snapclone.objects;

/**
 * Created by Mike on 2/23/18.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import floatingheads.snapclone.R;

public class CustomListAdapter<T extends Friend> extends ArrayAdapter<T> {

    public static int FRIENDS_SCREEN = 0;
    public static int MESSAGES_SCREEN = 1;

    private int screen_type;

    public CustomListAdapter(Context context, ArrayList<T> friends, int screen_type) {
        super(context, R.layout.custom_contact, friends);
        this.screen_type = screen_type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater friendsInflator = LayoutInflater.from(getContext());
        int resource = (screen_type == FRIENDS_SCREEN) ? R.layout.custom_friend : R.layout.custom_contact;

        View customFriendView = friendsInflator.inflate(resource, parent, false);

        Friend singleFriend = getItem(position);
        TextView friendName = (TextView) customFriendView.findViewById(R.id.friend_name);
        if (screen_type == MESSAGES_SCREEN) {
            Contact singleContact = (Contact) singleFriend;
            TextView lastMessage = (TextView) customFriendView.findViewById(R.id.last_message);
            lastMessage.setText(singleContact.getLastMessage());
        }
        ImageView avatar = (ImageView) customFriendView.findViewById(R.id.avatar);

        String name = singleFriend.getUserFirstName() + " " + singleFriend.getUserLastName();
        friendName.setText(name);

        if (singleFriend.getAvatar() != null) {
            avatar.setImageBitmap(singleFriend.getAvatar());
        } else {
            avatar.setImageResource(R.mipmap.avatar);
        }

        return customFriendView;
    }
}
