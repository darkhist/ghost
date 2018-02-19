package floatingheads.snapclone;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import floatingheads.snapclone.app.AppController;


public class AddFriendsActivity extends MainActivity {

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

//        VolleyActions va = new VolleyActions(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeStringRequest("http://proj-309-vc-4.cs.iastate.edu");
            }
        });
    }


    public void makeStringRequest(String url) {
        TextView mTextView = (TextView) findViewById(R.id.textView);
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                String tmp = "Response is " + response.substring(0,500);
                mTextView.setText(tmp);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText(error.toString());
            }
        });

        // Add the request to the RequestQueue.
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
