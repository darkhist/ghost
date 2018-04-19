package floatingheads.snapclone.fragments;

import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import floatingheads.snapclone.R;
import floatingheads.snapclone.adapters.ChatAdapter;
import floatingheads.snapclone.objects.Chat;

public class ChatFragment extends Fragment {

    private DatabaseReference databaseRef;
    private EditText chatInput;
    private ChatAdapter chatAdapter;

    // Constructor
    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        createConnection();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chat, container, false);

        chatInput = root.findViewById(R.id.chat_input);

        chatInput.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // TODO: Don't hardcode values for chatID and chatName
                Chat chat = new Chat();
                chat.setID("1");
                chat.setMessage(chatInput.getText().toString());
                chat.setName("Quinn");
                databaseRef.child(String.valueOf(new Date().getTime())).setValue(chat);
                return true;
            }
        });

        RecyclerView chat = (RecyclerView) root.findViewById(R.id.chat_message);
        if (VERSION.SDK_INT >= VERSION_CODES.M) {
            chat.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        chatAdapter = new ChatAdapter();
        chat.setAdapter(chatAdapter);

        return root;
    }

    private void createConnection() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseRef = db.getReference();
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("FirebaseDatabase", "Suceess");
                handleEvent(dataSnapshot);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FirebaseDatabase", "Failure" + databaseError.getMessage());
            }
        });
    }

    private void handleEvent(DataSnapshot dataSnapshot) {
        chatAdapter.clearData();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Chat chat = ds.getValue(Chat.class);
            chatAdapter.addData(chat);
        }
        chatAdapter.notifyDataSetChanged();
    }
}
