package floatingheads.snapclone.objects;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Mike on 2/25/18.
 */

public class MessagesView extends ListView {

    ArrayList<Contact> contactArrayList;
    Context context;

    // default constructor should never get called
    public MessagesView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public MessagesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public MessagesView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public void setContents(ArrayList<Contact> contents) {
        contactArrayList = contents;
        ListAdapter la = new CustomListAdapter(context, contactArrayList, CustomListAdapter.MESSAGES_SCREEN);
        setAdapter(la);
    }

    public void init() {
        setOnItemClickListener(
                (AdapterView<?> parent, View view, int position, long id) -> {
                    Contact contact = (Contact) parent.getItemAtPosition(position);
                    String name = contact.getUserFirstName() + " " + contact.getUserLastName();
                    Toast.makeText(this.getContext(), name, Toast.LENGTH_SHORT).show();

                    moveItemToTop(contact);
                }
        );
    }

    /**
     * Moves contact from current position in messages list to the top.
     * Useful when user gets new messages which need to be moved to the top of the messages list.
     *
     * @param contact
     */
    public void moveItemToTop(Contact contact) {
        contactArrayList.remove(contact);
        contactArrayList.add(0, contact);

        setAdapter(getAdapter()); // refresh adapter
    }

    /**
     * Returns the contents of the MessageView as an ArrayList of object Contact.
     *
     * @return
     */
    public ArrayList<Contact> getContents() {
        return contactArrayList;
    }
}
