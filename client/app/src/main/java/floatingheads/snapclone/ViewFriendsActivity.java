package floatingheads.snapclone;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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

        // create overall master container
        RelativeLayout parentContainer = (RelativeLayout) findViewById(R.id.rl);

        // create scrollable container for friends and set width/height to parents width/height
        ScrollView scrollContainer = new ScrollView(this);
        scrollContainer.setLayoutParams(new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT, ScrollView.LayoutParams.MATCH_PARENT));

        // create linear vertical container for buttons and set width/height to scrollContainer's width/height
        LinearLayout buttonContainer = new LinearLayout(this);
        buttonContainer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        buttonContainer.setOrientation(LinearLayout.VERTICAL);
        scrollContainer.addView(buttonContainer); // add buttonContainer to scrollContainer

        // add "i" buttons and label them accordingly
        for (int i = 0; i < 10; i++) {
            Button btn = new Button(this);
            btn.setWidth(buttonContainer.getWidth());
            String btnText = "Sample Friend " + i;
            btn.setText(btnText);
            btn.setHeight(150);
//            btn.setOnClickListener(friendClickListener);  // create button clicklistener

            buttonContainer.addView(btn);
        }

        // add scrollContainer to master container
        parentContainer.addView(scrollContainer);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}