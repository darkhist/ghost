package floatingheads.snapclone.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import org.json.JSONException;
import org.json.JSONObject;

import floatingheads.snapclone.fragments.FriendsFragment;
import floatingheads.snapclone.fragments.MessagesFragment;
import floatingheads.snapclone.fragments.NotisFragment;
import floatingheads.snapclone.fragments.ProfileFragment;
import floatingheads.snapclone.R;
import floatingheads.snapclone.objects.User;
import floatingheads.snapclone.objects.VolleyActions;

public class NavBarActivity extends AppCompatActivity {

    // User Information
    public User masterUser;

    //
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private ProfileFragment profileFragment;
    private FriendsFragment friendsFragment;
    private MessagesFragment messagesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bar);

//        String strUid = getIntent().getStringExtra("SESSION_UID");
//        int uid = Integer.parseInt(strUid);

//        int tempID = getIntent().getExtras().getInt("uid");
//        String firstName = getIntent().getExtras().getString("firstName");
//        String lastName = getIntent().getExtras().getString("lastName");
//        String username = getIntent().getExtras().getString("username");
//        String email = getIntent().getExtras().getString("email");

        // create bundle to pass user data< to other fragments

        // users
        Bundle masterUserBundle = new Bundle();
        masterUserBundle.putInt("uid", getIntent().getExtras().getInt("uid"));
        masterUserBundle.putString("firstName", getIntent().getExtras().getString("firstName"));
        masterUserBundle.putString("lastName", getIntent().getExtras().getString("lastName"));
        masterUserBundle.putString("username", getIntent().getExtras().getString("username"));
        masterUserBundle.putString("email", getIntent().getExtras().getString("email"));
//        // friends
//        masterUserBundle.putString("friends", getIntent().getExtras().getString("friends"));
//        masterUserBundle.putString("pending", getIntent().getExtras().getString("pending"));
//        masterUserBundle.putString("rejected", getIntent().getExtras().getString("rejected"));
//        masterUserBundle.putString("blocked", getIntent().getExtras().getString("blocked"));

        mMainNav = (BottomNavigationView) findViewById(R.id.navigation);
        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);

        profileFragment = new ProfileFragment();
        profileFragment.setArguments(masterUserBundle);

        friendsFragment = new FriendsFragment();
        friendsFragment.setArguments(masterUserBundle);
//        notisFragment = new NotisFragment();
        messagesFragment = new MessagesFragment();

        setFragment(messagesFragment); // sets default fragment to messages
        mMainNav.getMenu().getItem(0).setChecked(true); // selects message nav item as default

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

//                    case R.id.nav_notifications:
//                         setFragment(notisFragment);
//                        return true;

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
}
