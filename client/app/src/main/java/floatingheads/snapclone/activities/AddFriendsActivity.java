package floatingheads.snapclone.activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import floatingheads.snapclone.R;
import floatingheads.snapclone.objects.User;
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

        UsersView usersView = (UsersView) findViewById(R.id.users_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        MaterialSearchView searchView = (MaterialSearchView) findViewById(R.id.add_search_view);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        VolleyActions va = new VolleyActions();
        va.makeJSONarrayRequest(usersURL, usersView); //pulls users table from database and populates listview

        usersView.setOnItemClickListener(
                (AdapterView<?> parent, View view, int position, long id) -> {
                    User user = (User) parent.getItemAtPosition(position);
                    int userId = user.getId();
                    /*
                    launch profile activity
                    profile activity will use custom xml layout to display user's profile
                    same xml layout will be used in profile fragment to display own profile
                     */
                }
        );
    }
}
