package floatingheads.snapclone.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import floatingheads.snapclone.activities.MessagesListActivity;
import floatingheads.snapclone.activities.SignUpActivity;
import floatingheads.snapclone.objects.Contact;
import floatingheads.snapclone.objects.MessagesView;
import floatingheads.snapclone.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment {

    public MessagesFragment() {
        // Required empty public constructor
    }

    /**
     * Return View which contains Message data from friends
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
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

        // TODO integrate with firebase
        ArrayList<Contact> contactArrayList = new ArrayList<>();
        contactArrayList.add(new Contact(1, "Quinn", "Salas", "yo wassup homie?"));
        contactArrayList.add(new Contact(2, "Akira", "Demoss", "New Multimedia Message"));
        contactArrayList.add(new Contact(4, "Simanta", "Mitra", "Congrats your team is green."));

        MessagesView friendsList = (MessagesView) inflatedView.findViewById(R.id.messagesListView);
        friendsList.setContents(contactArrayList);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the Create New Message Screen
                Intent i = new Intent(getActivity().getApplicationContext(), MessagesListActivity.class);
                startActivity(i);
            }
        });

        return inflatedView;
    }

}
