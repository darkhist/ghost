package floatingheads.snapclone.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

import floatingheads.snapclone.R;
import floatingheads.snapclone.objects.UsersView;
import floatingheads.snapclone.objects.VolleyActions;



public class AddFriendsActivity extends MainActivity {

    private Context context = this;
    private String usersURL = "http://proj-309-vc-4.cs.iastate.edu:3000/users";
    private String friendsURL = "http://proj-309-vc-4.cs.iastate.edu:3000/friends";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        UsersView usersView = findViewById(R.id.users_view);

        VolleyActions va = new VolleyActions();
        va.makeJSONarrayRequest(usersURL, usersView); //pulls users table from database and populates listview
    }
}
