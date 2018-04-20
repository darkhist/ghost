package floatingheads.snapclone.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.Date;

import floatingheads.snapclone.R;
import floatingheads.snapclone.adapters.ChatAdapter;
import floatingheads.snapclone.objects.Chat;

public class ChatFragment extends Fragment {

    private DatabaseReference databaseRef;
    private EditText chatInput;
    private ChatAdapter chatAdapter;
    private ImageButton attachImage;
    private ImageView gallery;
    private final int GALLERY_REQUEST = 1;

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST:
                    Uri selectedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                        gallery.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
            }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chat, container, false);

        chatInput = root.findViewById(R.id.chat_input);
        attachImage = root.findViewById(R.id.attach);

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

        attachImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/* video/*");
                startActivityForResult(Intent.createChooser(galleryIntent, "Select Image"), GALLERY_REQUEST);
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
