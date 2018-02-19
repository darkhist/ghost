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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import floatingheads.snapclone.app.AppController;

    public class ViewFriendsActivity extends AppCompatActivity {

    private TextView mTextMessage;
        // create overall master container

    public LinearLayout buttonContainer = new LinearLayout(this);
    public RelativeLayout parentContainer = (RelativeLayout) findViewById(R.id.rl);
    public ScrollView scrollContainer = new ScrollView(this);

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

        //makeStringRequest("http://proj-309-vc-4.cs.iastate.edu:3000/friends");
        //makeJSONarrayRequest("https://jsonplaceholder.typicode.com/posts");
        makeJSONobjRequest("http://ip.jsontest.com/");
        //makeJSONobjRequest("http://jsonplaceholder.typicode.com/posts/1");
        // create scrollable container for friends and set width/height to parents width/height

        scrollContainer.setLayoutParams(new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT, ScrollView.LayoutParams.MATCH_PARENT));
        // create linear vertical container for buttons and set width/height to scrollContainer's width/height
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

    public void LoadFriends(int number){
        int i = number;
        // add "i" buttons and label them accordingly
        for ( i = 0; i < 10; i++) {
            Button btn = new Button(this);
            btn.setWidth(buttonContainer.getWidth());
            String btnText = "Sample Friend " + i;
            btn.setText(btnText);
            btn.setHeight(150);
          //btn.setOnClickListener(friendClickListener);  // create button clicklistener
            buttonContainer.addView(btn);
        }

        // add scrollContainer to master container
        parentContainer.addView(scrollContainer);
    }

    public void makeStringRequest(String url) {
        TextView mTextView = (TextView) findViewById(R.id.textView);
        // Request a string response from the provided URL.
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                String tmp = "Response is " + response;
                mTextView.setText(tmp);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText(error.toString());
            }
        });
        // Add the request to the RequestQueue.
        AppController.getInstance().addToRequestQueue(req);
    }

    public void makeJSONarrayRequest(String url) {
        TextView mTextView = (TextView) findViewById(R.id.textView);
        JsonArrayRequest req = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    /*
                    jsonResponse = "";
                    for (int i = 0; i < response.length(); i++) {

                    }*/
                    VolleyLog.v("Response:%n %s", response.toString(4));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });
        // add the request object to the queue to be executed
        AppController.getInstance().addToRequestQueue(req);
    }

    public void makeJSONobjRequest(String url) {
        TextView mTextView = (TextView) findViewById(R.id.textView);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", "AbCdEfGh123456");
        JsonObjectRequest req = new JsonObjectRequest(url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    VolleyLog.v("Response:%n %s", response.toString(4));
                    String ip = response.getString("ip");
                            /*
                            String userId = (String) response.get("userId");
                            String id = (String) response.get("id");
                            String title = response.getString("title");
                            String body = response.getString("body");
                            */
                    mTextView.setText(ip);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });
        // add the request object to the queue to be executed
        AppController.getInstance().addToRequestQueue(req);
    }
}
