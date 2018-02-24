package floatingheads.snapclone;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.icu.util.Freezable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NavBarActivity extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private ProfileFragment profileFragment;
    private FriendsFragment friendsFragment;
    private NotisFragment notisFragment;
    private MessagesFragment messagesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bar);

        mMainNav = (BottomNavigationView) findViewById(R.id.navigation);
        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);

        profileFragment = new ProfileFragment();
        friendsFragment = new FriendsFragment();
        notisFragment = new NotisFragment();
        messagesFragment = new MessagesFragment();

        setFragment(profileFragment);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_profile:
                         setFragment(profileFragment);
                        return true;

                    case R.id.nav_friends:
                        setFragment(friendsFragment);
                        return true;

                    case R.id.nav_notifications:
                         setFragment(notisFragment);
                        return true;

                    case R.id.nav_messages:
                         setFragment(messagesFragment);
                        return true;
                }
                return false;
            }
        });

    }

    private void setFragment(Fragment f) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, f);
        fragmentTransaction.commit();
    }

//        Friend[] friends = {
//                new Friend(1, "Quinn", "Salas", "yo wassup homie?"),
//                new Friend(2, "Akira", "Demoss", "opencv is a bitch"),
//                new Friend(4, "Simanta", "Mitra", "Congrats your team is green.")
//        }; // will change to custom list item
//
//        ListAdapter la = new FriendsListAdapter(this, friends);
//        ListView friendsList = (ListView) findViewById(R.id.friendsListView);
//        friendsList.setAdapter(la);
//
//        friendsList.setOnItemClickListener(
//                (AdapterView<?> parent, View view, int position, long id) -> {
//                    String friend = String.valueOf(parent.getItemAtPosition(position));
//                    Toast.makeText(NavBarActivity.this, friend, Toast.LENGTH_SHORT).show();
//                }
//        );
}
