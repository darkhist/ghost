package floatingheads.snapclone;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
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

        Toolbar toolbar = (Toolbar) inflatedView.findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.mipmap.ic_add_box_white_24dp);
        toolbar.setTitle("Messages");
        toolbar.setTitleTextColor(Color.WHITE);

        Context messagesFragmentContext = this.getContext();

        Contact[] contacts = {
                new Contact(1, "Quinn", "Salas", "yo wassup homie?"),
                new Contact(2, "Akira", "Demoss", "New Multimedia Message"),
                new Contact(4, "Simanta", "Mitra", "Congrats your team is green.")
        }; // will change to custom list item

        ListAdapter la = new CustomListAdapter(this.getContext(), contacts, CustomListAdapter.MESSAGES_SCREEN);
        ListView friendsList = (ListView) inflatedView.findViewById(R.id.messagesListView);
        friendsList.setAdapter(la);

        friendsList.setOnItemClickListener(
                (AdapterView<?> parent, View view, int position, long id) -> {
                    Contact contact = (Contact) parent.getItemAtPosition(position);
                    String name = contact.getUserFirstName() + " " + contact.getUserLastName();
                    Toast.makeText(this.getContext(), name, Toast.LENGTH_SHORT).show();
                }
        );

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
            }
        });

        return inflatedView;
    }

}
