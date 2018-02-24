package floatingheads.snapclone;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class ViewFriendsActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_friends);

        Friend[] friends = {
                new Friend(1, "Quinn", "Salas", "yo wassup homie?"),
                new Friend(2, "Akira", "Demoss", "opencv is a bitch"),
                new Friend(4, "Simanta", "Mitra", "Congrats your team is green.")
        }; // will change to custom list item

        ListAdapter la = new FriendsListAdapter(this, friends);
        ListView friendsList = (ListView) findViewById(R.id.friendsListView);
        friendsList.setAdapter(la);

        friendsList.setOnItemClickListener(
                (AdapterView<?> parent, View view, int position, long id) -> {
                    String friend = String.valueOf(parent.getItemAtPosition(position));
                    Toast.makeText(ViewFriendsActivity.this, friend, Toast.LENGTH_SHORT).show();
                }
        );

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
