package floatingheads.snapclone.objects;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import floatingheads.snapclone.R;
import floatingheads.snapclone.activities.NavBarActivity;
import floatingheads.snapclone.volleyController.AppController;

/**
 * Created by Mike on 2/26/18.
 */

public class VolleyActions {

    private View view;
    private ViewGroup viewGroup;
    private Context context;

    private JSONArray jsonArray;

    public static int VIEW = 0;
    public static int VIEW_GROUP = 1;

    public VolleyActions(Context context) {
        this.context = context;
        view = null;
    }

    public VolleyActions(View view) {
        this.view = view;
    }

    public VolleyActions(ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
    }

//    public JSONArray makeSyncJSONArrayRequest(String url) {
//        RequestFuture<JSONArray> future = RequestFuture.newFuture();
//        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, new JSONArray(), future, future);
//
//        AppController.getInstance().addToRequestQueue(request);
//
//        try {
//            Toast.makeText(context, "Got Response!", Toast.LENGTH_SHORT).show();
//            return ;
//        } catch (InterruptedException e) {
//            Toast.makeText(context, "Connection interrupted", Toast.LENGTH_LONG).show();
//        } catch (ExecutionException e) {
//            Toast.makeText(context, "Connection error", Toast.LENGTH_LONG).show();
//        } catch (TimeoutException e) {
//            Toast.makeText(context, "Connection timed out", Toast.LENGTH_LONG).show();
//        }
//
//        return null;
//    }

    public void makeJSONArrayRequest(String url) {
        JsonArrayRequest jar = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                setJSONArray(response);
                if (response != null) {
                    Toast.makeText(context, "Got Response!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });
        AppController.getInstance().addToRequestQueue(jar);
    }

    public void setJSONArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public JSONArray getJSONArray() { // must be polled
        return jsonArray;
    }

    public void makeStringRequest(String url, Object o) {
        // Request a string response from the provided URL.
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                String tmp = "Response is " + response;
                if (o instanceof TextView && o != null) {
                    TextView mTextView = (TextView) o;
                    mTextView.setText(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO
            }
        });

        // Add the request to the RequestQueue.
        AppController.getInstance().addToRequestQueue(req);
    }


    public void makeJSONArrayRequest(String url, Object o) {
        JsonArrayRequest req = new JsonArrayRequest(url, response -> {

            if (!(o instanceof ListView) || !(o instanceof View) || o == null) {
                return;
            }

            if (o instanceof ListView) {

                try {

                    ArrayList<User> userArrayList = new ArrayList<>();

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        int id = jsonObject.getInt("userID");
//                        String phone = jsonObject.getString("phone_number");
                        String first = jsonObject.getString("first_name");
                        String last = jsonObject.getString("last_name");
                        String username = jsonObject.getString("username");
                        String email = jsonObject.getString("email");

                        User user = new User(id, first, last, username, email);
                        userArrayList.add(user);
                    }

                    if (o instanceof MessagesView) {
                        MessagesView messagesView = (MessagesView) o;
                        messagesView.setContents(userArrayList);
                    }
                    if (o instanceof UsersView) {
                        UsersView usersView = (UsersView) o;
                        usersView.setContents(userArrayList);
                    }

                    VolleyLog.v("Response:%n %s", response.toString(4));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, error -> VolleyLog.e("Error: ", error.getMessage()));

        // add the request object to the queue to be executed
        AppController.getInstance().addToRequestQueue(req);
    }

    public void makeJSONobjRequest(String url, Object o) {
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

    private void parseJSON() {

    }

    public void setViewGroup(ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
    }

    private void updateViewGroup(ListView listView) {

    }

    private void updateViewGroup(MessagesView messagesView) {

    }

    public void setView(View view) {
        this.view = view;
    }

    private void updateTextView(TextView textView, String response) {
        textView.setText(response);
    }
}
