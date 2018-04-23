package floatingheads.snapclone.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.UploadTask.TaskSnapshot;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Random;

import floatingheads.snapclone.R;
import floatingheads.snapclone.adapters.ChatAdapter;
import floatingheads.snapclone.controllers.SwipeController;
import floatingheads.snapclone.controllers.SwipeControllerActions;
import floatingheads.snapclone.objects.Message;

public class ChatFragment extends Fragment {

    private DatabaseReference databaseRef;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private StorageReference imgRef = storageRef.child("attachment" + new Random().nextInt(101) + ".jpg");

    private EditText chatInput;
    private View root;

    private SwipeController swipeController = null;
    private ChatAdapter chatAdapter = new ChatAdapter();

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
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST:
                    Uri selectedImage = data.getData();
                    try {
                        // Get Image
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);

                        // Prepare Image for Upload to Firebase
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] img = baos.toByteArray();

                        // Upload Image to Firebase
                        UploadTask uploadTask = imgRef.putBytes(img);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Log.d("UploadError", exception.toString());
                            }
                        }).addOnSuccessListener(new OnSuccessListener<TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(getContext(), "Image Uploaded!", Toast.LENGTH_SHORT).show();
                                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                // TODO: Display Image
                            }
                        });
                    } catch (Exception e) {
                        Log.i("TAG", "Exception: " + e);
                    }
                    break;
            }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_chat, container, false);
        chatInput = root.findViewById(R.id.chat_input);
        ImageButton attachImage = root.findViewById(R.id.attach);

        setupRecyclerView();

        chatInput.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Message msg = new Message();
                msg.setID(1);
                msg.setUser("Quinn");
                msg.setMessage(chatInput.getText().toString());
                // msg.setImageURI(imageURI);
                msg.setTimestamp(System.currentTimeMillis());
                databaseRef.child(String.valueOf(new Date().getTime())).setValue(msg);
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

        RecyclerView recyclerView = root.findViewById(R.id.chat_messages);
        if (VERSION.SDK_INT >= VERSION_CODES.M) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        return root;
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = root.findViewById(R.id.chat_messages);
        recyclerView.setAdapter(chatAdapter);

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                chatAdapter.remove(position);
                chatAdapter.notifyItemRemoved(position);
                chatAdapter.notifyItemRangeChanged(position, chatAdapter.getItemCount());
            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
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
        chatAdapter.clearData();
        chatInput.setText(null);

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Message msg = ds.getValue(Message.class);
            chatAdapter.addData(msg);
        }
        chatAdapter.notifyDataSetChanged();
    }
}
