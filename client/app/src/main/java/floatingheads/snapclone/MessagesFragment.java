package floatingheads.snapclone;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment {


    public MessagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView = inflater.inflate(R.layout.fragment_messages, container, false);

        Friend[] friends = {
                new Friend(1, "Quinn", "Salas", "yo wassup homie?"),
                new Friend(2, "Akira", "Demoss", "New Multimedia Message"),
                new Friend(4, "Simanta", "Mitra", "Congrats your team is green.")
        }; // will change to custom list item

        ListAdapter la = new CustomListAdapter(this.getContext(), friends, CustomListAdapter.MESSAGES_SCREEN);
        ListView friendsList = (ListView) inflatedView.findViewById(R.id.messagesListView);
        friendsList.setAdapter(la);

        friendsList.setOnItemClickListener(
                (AdapterView<?> parent, View view, int position, long id) -> {
                    Friend friend = (Friend) parent.getItemAtPosition(position);
                    String name = friend.getUserFirstName() + " " + friend.getUserLastName();
                    Toast.makeText(this.getContext(), name, Toast.LENGTH_SHORT).show();
                }
        );

        return inflatedView;
    }

}
