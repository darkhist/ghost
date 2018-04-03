package floatingheads.snapclone.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import floatingheads.snapclone.activities.AddFriendsActivity;
import floatingheads.snapclone.objects.CustomListAdapter;
import floatingheads.snapclone.objects.Friend;
import floatingheads.snapclone.R;
import floatingheads.snapclone.objects.User;
import floatingheads.snapclone.objects.VolleyActions;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment {

    private MaterialSearchView searchView;

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

        VolleyActions va = new VolleyActions(getContext());
        JSONObject friendsData = new JSONObject();
        JSONArray friendsJsonArray = va.makeSyncJSONArrayRequest("http://proj-309-vc-4.cs.iastate.edu:3000/friends");

        int counter = 0;
        int uid = -1;
        String friends = null;
        String pending = null;
        String rejected = null;
        String blocked = null;

        try {
            while (uid != masterUser.getId() && counter < friendsJsonArray.length()) {
                friendsData = friendsJsonArray.getJSONObject(counter);
                uid = friendsData.getInt("userID");
                counter++;
            }
            if (counter == friendsJsonArray.length()) {
                Toast.makeText(getContext(), "Error retrieving friends", Toast.LENGTH_LONG);
            } else {
                friends = friendsData.getString("friends");
                pending = friendsData.getString("pending");
                rejected = friendsData.getString("rejected");
                blocked = friendsData.getString("blocked");
            }
        } catch (JSONException e) {
            Toast.makeText(getContext(), "Error retrieving friends", Toast.LENGTH_LONG);
        }

        String[] temparr = friends.split(",");
        int[] friendsArray = new int[temparr.length];
        for (int i = 0; i < temparr.length; i++) {
            try {
                friendsArray[i] = Integer.parseInt(temparr[i]);
            } catch (NumberFormatException e) {
                // shouldn't ever happen given how this entry is formatted
                Toast.makeText(getContext(), "Database entry error", Toast.LENGTH_LONG);
                break;
            }
        }

        // iterate through all users with friends id and load into friendArrayList //
        ArrayList<Friend> friendArrayList = new ArrayList<>();
        JSONArray friendsUserDataJsonArray = va.makeSyncJSONArrayRequest("http://proj-309-vc-4.cs.iastate.edu:3000/users");
        JSONObject friendsUserData;

        int friendsCounter = friendsArray.length;
        int usersCounter = friendsUserDataJsonArray.length();
        while (friendsCounter > 0 && usersCounter > 0) {
            try {
                friendsUserData = friendsUserDataJsonArray.getJSONObject(usersCounter);
                for (int i = 0; i < friendsArray.length; i++) {
                    if (friendsUserData.getInt("userID") == friendsArray[i]) {
                        friendsCounter--;
                        friendArrayList.add(new Friend(
                                friendsUserData.getInt("userID"),
                                friendsUserData.getString("first_name"),
                                friendsUserData.getString("last_name"),
                                Friend.STATUS_ACCEPTED
                        ));
                    }
                }
                usersCounter--;
            } catch (JSONException e) {
                Toast.makeText(getContext(), "Error retrieving friends data", Toast.LENGTH_LONG);
            }
        }


        // add friends to arraylist
        // TODO get these friends from database
//        friendArrayList.add(new Friend(1, "Quinn", "Salas", Friend.STATUS_ACCEPTED));
//        friendArrayList.add(new Friend(2, "Akira", "Demoss", Friend.STATUS_ACCEPTED));
//        friendArrayList.add(new Friend(4, "Simanta", "Mitra", Friend.STATUS_ACCEPTED));
//        friendArrayList.add(new Friend(6,"Mark", "Hammill", Friend.STATUS_ACCEPTED));
//        friendArrayList.add(new Friend(12,"Esperanza", "Spalding", Friend.STATUS_ACCEPTED));
//        friendArrayList.add(new Friend(13, "Harry", "Potter", Friend.STATUS_ACCEPTED));
//        friendArrayList.add(new Friend(21, "Hermione", "Granger", Friend.STATUS_ACCEPTED));
//        friendArrayList.add(new Friend(25, "Vamsi", "Calpakkam", Friend.STATUS_ACCEPTED));
//        friendArrayList.add(new Friend(34,"Tom", "Brady", Friend.STATUS_ACCEPTED));
//        friendArrayList.add(new Friend(54, "Magic","Johnson", Friend.STATUS_ACCEPTED));
//        friendArrayList.add(new Friend(56, "Michael", "Jordan", Friend.STATUS_ACCEPTED));


        Collections.sort(friendArrayList); // sort list in alphabetical order

        ListAdapter la = new CustomListAdapter(this.getContext(), friendArrayList, CustomListAdapter.FRIENDS_SCREEN);
        ListView friendsList = (ListView) inflatedView.findViewById(R.id.friendsListView);
        friendsList.setAdapter(la);

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
