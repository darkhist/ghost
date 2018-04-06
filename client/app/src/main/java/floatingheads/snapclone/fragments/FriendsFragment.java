package floatingheads.snapclone.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.support.v7.widget.Toolbar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.cedarsoftware.util.io.JsonWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Timer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import floatingheads.snapclone.activities.AddFriendsActivity;
import floatingheads.snapclone.objects.CustomListAdapter;
import floatingheads.snapclone.objects.Friend;
import floatingheads.snapclone.R;
import floatingheads.snapclone.objects.User;
import floatingheads.snapclone.objects.VolleyActions;
import floatingheads.snapclone.objects.VolleyCallback;
import floatingheads.snapclone.volleyController.AppController;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment {

    private MaterialSearchView searchView;
    private String usersURL = "http://proj-309-vc-4.cs.iastate.edu:3000/users";
    private String friendsURL = "http://proj-309-vc-4.cs.iastate.edu:3000/friends";

    User masterUser;


    public FriendsFragment() {
        super();
        searchView = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView = inflater.inflate(R.layout.fragment_friends, container, false);

        Context friendsFragmentContext = this.getContext();

        setHasOptionsMenu(true);

        // create master user
        masterUser = new User(
                getArguments().getInt("uid"),
                getArguments().getString("firstName"),
                getArguments().getString("lastName"),
                getArguments().getString("username"),
                getArguments().getString("email")
        );

        Toolbar toolbar = (Toolbar) inflatedView.findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.mipmap.ic_person_add_white_24dp);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Friends");
        toolbar.setTitleTextColor(Color.WHITE);

        searchView = (MaterialSearchView) inflatedView.findViewById(R.id.search_view);

        VolleyActions va = new VolleyActions(friendsFragmentContext);
        ArrayList<Friend> friendArrayList = new ArrayList<>();
        ListView friendsList = (ListView) inflatedView.findViewById(R.id.friendsListView);

        va.makeJSONArrayRequest(friendsURL, new VolleyCallback() {

            @Override
            public void onSuccessResponse(JSONArray result) {
                JSONObject user;
                String friends = null;
                int[] friendsArr;

                for (int i = 0; i < result.length(); i++) {
                    try {
                        if ((user = result.getJSONObject(i)).getInt("userID") == masterUser.getId()) {
                            friends = user.getString("friends");
                            break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (friends == null) {
                    Toast.makeText(friendsFragmentContext, "Unable to load user data", Toast.LENGTH_LONG).show();
                    return;
                }

                String[] tempArr = friends.split(",");
                friendsArr = new int[tempArr.length];
                for (int i = 0; i < tempArr.length; i++) {
                    friendsArr[i] = Integer.parseInt(tempArr[i]);
                }

                va.makeJSONArrayRequest(usersURL, new VolleyCallback() {
                    JSONObject user;

                    @Override
                    public void onSuccessResponse(JSONArray result) {
                        int friendsCounter = friendsArr.length;
                        int usersIndex = 0;
                        while (friendsCounter > 0 && usersIndex < result.length()) {
                            try {
                                for (int i = 0; i < friendsArr.length; i++) {
                                    if ((user = result.getJSONObject(usersIndex++)).getInt("userID") == friendsArr[i]) {
                                        friendArrayList.add(new Friend(
                                                user.getInt("userID"),
                                                user.getString("first_name"),
                                                user.getString("last_name"),
                                                Friend.STATUS_ACCEPTED
                                        ));
                                        friendsCounter--;
                                    }
//                                    Log.d("callback2", "" + user.getInt("userID"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
//                        Log.d("callback2", friendArrayList.toString());
                        //
                        Collections.sort(friendArrayList);
                        ListAdapter la = new CustomListAdapter(friendsFragmentContext, friendArrayList, CustomListAdapter.FRIENDS_SCREEN);
                        friendsList.setAdapter(la);
                    }
                });
            }
        });

        // add friends to arraylist
        // TODO get these friends from database
//        friendArrayList.add(new Friend(1, "Quinn", "Salas", Friend.STATUS_ACCEPTED));
//        friendArrayList.add(new Friend(2, "Akira", "Demoss", Friend.STATUS_ACCEPTED));
//        friendArrayList.add(new Friend(4, "Simanta", "Mitra", Friend.STATUS_ACCEPTED));
//        friendArrayList.add(new Friend(6, "Mark", "Hammill", Friend.STATUS_ACCEPTED));
//        friendArrayList.add(new Friend(12, "Esperanza", "Spalding", Friend.STATUS_ACCEPTED));
//        friendArrayList.add(new Friend(13, "Harry", "Potter", Friend.STATUS_ACCEPTED));
//        friendArrayList.add(new Friend(21, "Hermione", "Granger", Friend.STATUS_ACCEPTED));
//        friendArrayList.add(new Friend(25, "Vamsi", "Calpakkam", Friend.STATUS_ACCEPTED));
//        friendArrayList.add(new Friend(34, "Tom", "Brady", Friend.STATUS_ACCEPTED));
//        friendArrayList.add(new Friend(54, "Magic","Johnson", Friend.STATUS_ACCEPTED));
//        friendArrayList.add(new Friend(56, "Michael", "Jordan", Friend.STATUS_ACCEPTED));

        friendsList.setOnItemClickListener(
                (AdapterView<?> parent, View view, int position, long id) -> {
                    Friend friend = (Friend) parent.getItemAtPosition(position);
                    String name = friend.getFirstName() + " " + friend.getLastName();
                    Toast.makeText(this.getContext(), name, Toast.LENGTH_SHORT).show();
                }
        );

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(friendsFragmentContext, AddFriendsActivity.class);
                startActivity(i);
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                // if closed, view goes to normal
                ListAdapter la = new CustomListAdapter(friendsFragmentContext, friendArrayList, CustomListAdapter.FRIENDS_SCREEN);
                ListView friendsList = (ListView) inflatedView.findViewById(R.id.friendsListView);
                friendsList.setAdapter(la);
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String name = "";
                if (newText != null && !newText.isEmpty()) {
                    ArrayList<Friend> listFound = new ArrayList<>();
                    for (Friend friend : friendArrayList) {

                        if (friend.getFirstName().toLowerCase().startsWith(newText.toLowerCase()) || friend.getLastName().toLowerCase().startsWith(newText.toLowerCase())) {
                            listFound.add(friend);
                        }

                        ListAdapter adapter = new CustomListAdapter(friendsFragmentContext, listFound, CustomListAdapter.FRIENDS_SCREEN);
                        friendsList.setAdapter(adapter);
                    }
                } else {
                    // search text is null
                    ListAdapter adapter = new CustomListAdapter(friendsFragmentContext, friendArrayList, CustomListAdapter.FRIENDS_SCREEN);
                    friendsList.setAdapter(adapter);
                }
                return true;
            }
        });

        return inflatedView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.search_menu_item, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
    }
}
