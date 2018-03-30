package floatingheads.snapclone.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import floatingheads.snapclone.R;
import floatingheads.snapclone.objects.Contact;
import floatingheads.snapclone.objects.MessagesView;

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

        ArrayList<Contact> contactArrayList = new ArrayList<>();
        contactArrayList.add(new Contact(1, "Quinn", "Salas", "yo wassup homie?"));
        contactArrayList.add(new Contact(2, "Akira", "Demoss", "New Multimedia Message"));
        contactArrayList.add(new Contact(4, "Simanta", "Mitra", "Congrats your team is green."));

        MessagesView friendsList = (MessagesView) inflatedView.findViewById(R.id.messagesListView);
        friendsList.setContents(contactArrayList);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Launch Chat Fragment
            }
        });

        return inflatedView;
    }

}
