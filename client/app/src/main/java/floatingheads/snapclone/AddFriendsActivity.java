package floatingheads.snapclone;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;


public class AddFriendsActivity extends AppCompatActivity {

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        LinearLayout ll = new LinearLayout(this);
        ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        ll.setOrientation(LinearLayout.VERTICAL);


        // Button to call OpenCV Camera Activity
        Button button = (Button) findViewById(R.id.button);
        TextView mTextView = (TextView) findViewById(R.id.textView);

        VolleyActions va = new VolleyActions(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                va.makeStringRequest("https://www.google.com");
                mTextView.setText(va.getStringResponse());
            }
        });
    }
}