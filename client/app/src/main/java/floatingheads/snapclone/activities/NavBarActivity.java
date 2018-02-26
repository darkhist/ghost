package floatingheads.snapclone.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import floatingheads.snapclone.fragments.FriendsFragment;
import floatingheads.snapclone.fragments.MessagesFragment;
import floatingheads.snapclone.fragments.NotisFragment;
import floatingheads.snapclone.fragments.ProfileFragment;
import floatingheads.snapclone.R;

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

        setFragment(messagesFragment);

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
}
