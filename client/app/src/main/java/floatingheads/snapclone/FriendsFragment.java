package floatingheads.snapclone;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment {


    public FriendsFragment() {
        super();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView = inflater.inflate(R.layout.fragment_friends, container, false);

                Friend[] friends = {
                new Friend(1, "Quinn", "Salas", "yo wassup homie?"),
                new Friend(2, "Akira", "Demoss", "opencv is a bitch"),
                new Friend(4, "Simanta", "Mitra", "Congrats your team is green.")
        }; // will change to custom list item

        ListAdapter la = new FriendsListAdapter(this.getContext(), friends);
        ListView friendsList = (ListView) inflatedView.findViewById(R.id.friendsListView);
        friendsList.setAdapter(la);

        friendsList.setOnItemClickListener(
                (AdapterView<?> parent, View view, int position, long id) -> {
                    String friend = String.valueOf(parent.getItemAtPosition(position));
                    Toast.makeText(this.getContext(), friend, Toast.LENGTH_SHORT).show();
                }
        );

        return inflatedView;
    }

}
