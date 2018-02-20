package floatingheads.snapclone;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import floatingheads.snapclone.app.AppController;

import static com.android.volley.Request.Method.GET;


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
                //makeStringRequest("https://api.androidhive.info/volley/person_array.json");
                //makeJSONarrayRequest("https://jsonplaceholder.typicode.com/posts");
                //makeJSONobjRequest("http://ip.jsontest.com/");
                makeJSONobjRequest("https://api.androidhive.info/volley/person_object.json");
            }
        });


    }


    public void makeStringRequest(String url) {
        TextView mTextView = (TextView) findViewById(R.id.textView);
        // Request a string response from the provided URL.
        StringRequest req = new StringRequest(GET, url, new Response.Listener<String>() {
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

    //Creates the new request
    public void makeJSONobjRequest(String url) {
        TextView mTextView = (TextView) findViewById(R.id.textView);
        HashMap<String, String> params = new HashMap<String, String>();
        JsonObjectRequest req = new JsonObjectRequest(GET, url, new JSONObject(params), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VolleyLog.v("Response:%n %s", response.toString(4));
                            mTextView.setText(response.toString());
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