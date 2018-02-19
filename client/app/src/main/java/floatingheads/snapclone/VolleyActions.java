package floatingheads.snapclone;

import android.content.Context;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by root on 2/18/18.
 */

public class VolleyActions {

    public Context context;
//    public ResponseInterpreter r;

    public VolleyActions(Context context) {
        this.context = context;
//        r = new ResponseInterpreter();
    }


    public void makeStringRequest(String url) {
//        TextView mTextView = (TextView) findViewById(R.id.textView);
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                String tmp = "Response is " + response.substring(0,500);
//                r.setText(tmp);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                r.setText("That didn't work!");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

//    public String getStringResponse() {
//        return r.getText();
//    }
//
//    class ResponseInterpreter {
//        String text;
//
//        public ResponseInterpreter() {
//            text = null;
//        }
//
//        public void setText(String str) {
//            text = str;
//        }
//
//        public String getText() {
//            return text;
//        }
//    }
}
