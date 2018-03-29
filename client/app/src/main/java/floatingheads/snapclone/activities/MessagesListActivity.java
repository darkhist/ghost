package floatingheads.snapclone.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
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
import floatingheads.snapclone.objects.Chat;

public class MessagesListActivity extends AppCompatActivity {

    private DatabaseReference databaseRef;
    private EditText chatInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_list);

        createConnection();

        chatInput = findViewById(R.id.chat_input);

        chatInput.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                Chat chat = new Chat();
                chat.setID("1");
                chat.setMessage(chatInput.getText().toString());
                chat.setName("Quinn");

                databaseRef.child(String.valueOf(new Date().getTime())).setValue(chat);

                return true;
            }
        });
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
        // TODO
    }


}
