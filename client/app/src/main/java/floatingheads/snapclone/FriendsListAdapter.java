package floatingheads.snapclone;

/**
 * Created by root on 2/23/18.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import floatingheads.snapclone.Friend;

class FriendsListAdapter extends ArrayAdapter<Friend> {

    public FriendsListAdapter(Context context, Friend[] friends) {
        super(context, R.layout.custom_friend, friends);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater friendsInflator = LayoutInflater.from(getContext());
        View customFriendView = friendsInflator.inflate(R.layout.custom_friend, parent, false);

        Friend singleFriend = getItem(position);
        TextView friendName = (TextView) customFriendView.findViewById(R.id.friend_name);
        TextView lastMessage = (TextView) customFriendView.findViewById(R.id.last_message);
        ImageView avatar = (ImageView) customFriendView.findViewById(R.id.avatar);

        String name = singleFriend.getUserFirstName() + " " + singleFriend.getUserLastName();
        friendName.setText(name);
        lastMessage.setText(singleFriend.getLastMessage());
        avatar.setImageResource(R.mipmap.avatar); // set image later

        return customFriendView;
    }
}
