package floatingheads.snapclone.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import floatingheads.snapclone.R;
import floatingheads.snapclone.fragments.ChatFragment;
import floatingheads.snapclone.fragments.FriendsFragment;
import floatingheads.snapclone.fragments.MessagesFragment;
import floatingheads.snapclone.fragments.ProfileFragment;
import floatingheads.snapclone.objects.User;

public class NavBarActivity extends AppCompatActivity {
    // User Information
    /**
     * User which contains all credentials of logged in user
     */
    public User masterUser;

    // UI
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    // Fragments
    private ProfileFragment profileFragment;
    private FriendsFragment friendsFragment;
    private MessagesFragment messagesFragment;
    private ChatFragment chatFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bar);

        // create bundle to pass user data to other fragments

        // users
        Bundle masterUserBundle = new Bundle();
        masterUserBundle.putInt("uid", getIntent().getExtras().getInt("uid"));
        masterUserBundle.putString("firstName", getIntent().getExtras().getString("firstName"));
        masterUserBundle.putString("lastName", getIntent().getExtras().getString("lastName"));
        masterUserBundle.putString("username", getIntent().getExtras().getString("username"));
        masterUserBundle.putString("email", getIntent().getExtras().getString("email"));

        mMainNav = findViewById(R.id.navigation);
        mMainFrame = findViewById(R.id.main_frame);

        profileFragment = new ProfileFragment();
        profileFragment.setArguments(masterUserBundle);

        friendsFragment = new FriendsFragment();
        friendsFragment.setArguments(masterUserBundle);

        messagesFragment = new MessagesFragment();
        chatFragment = new ChatFragment();

        setFragment(messagesFragment); // sets default fragment to messages
        mMainNav.getMenu().getItem(0).setChecked(true); // selects message nav item as default

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            /**
             * Opens Fragments for the user's profile, friends list, or messages.
             * @param item
             * @return
             */
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_profile:
                         setFragment(profileFragment);
                        return true;

                    case R.id.nav_friends:
                        setFragment(friendsFragment);
                        return true;

                    case R.id.nav_messages:
                         setFragment(messagesFragment);
                        return true;

                    case R.id.nav_chat:
                        setFragment(chatFragment);
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
