package floatingheads.snapclone;


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

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment {

    private MaterialSearchView searchView;


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

        Toolbar toolbar = (Toolbar) inflatedView.findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.mipmap.ic_person_add_white_24dp);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Friends");
        toolbar.setTitleTextColor(Color.WHITE);

        searchView = (MaterialSearchView) inflatedView.findViewById(R.id.search_view);

        // realistically we would use an arraylist to add friends.  I havent figured out how to
        // get arraylists to work with my CustomListAdapter so that is a TODO

        Friend[] friends = {
                new Friend(1, "Quinn", "Salas"),
                new Friend(2, "Akira", "Demoss"),
                new Friend(4, "Simanta", "Mitra"),
                new Friend(6,"Mark", "Hammill"),
                new Friend(12,"Esperanza", "Spalding"),
                new Friend(13, "Harry", "Potter"),
                new Friend(21, "Hermione", "Granger"),
                new Friend(25, "Vamsi", "Calpakkam"),
                new Friend(34,"Tom", "Brady"),
                new Friend(54, "Magic","Johnson"),
                new Friend(56, "Michael", "Jordan")
        }; // will change to custom list item

        ListAdapter la = new CustomListAdapter(this.getContext(), friends, CustomListAdapter.FRIENDS_SCREEN);
        ListView friendsList = (ListView) inflatedView.findViewById(R.id.friendsListView);
        friendsList.setAdapter(la);

        friendsList.setOnItemClickListener(
                (AdapterView<?> parent, View view, int position, long id) -> {
                    Friend friend = (Friend) parent.getItemAtPosition(position);
                    String name = friend.getUserFirstName() + " " + friend.getUserLastName();
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
                ListAdapter la = new CustomListAdapter(friendsFragmentContext, friends, CustomListAdapter.FRIENDS_SCREEN);
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
                    List<Friend> listFound = new ArrayList<>();
                    for (Friend friend : friends) {

                        if (friend.getUserFirstName().toLowerCase().startsWith(newText.toLowerCase()) || friend.getUserLastName().toLowerCase().startsWith(newText.toLowerCase())) {
                            listFound.add(friend);
                        }

                        ListAdapter adapter = new CustomListAdapter(friendsFragmentContext, listFound.toArray(new Friend[listFound.size()]), CustomListAdapter.FRIENDS_SCREEN);
                        friendsList.setAdapter(adapter);
                    }
                } else {
                    // search text is null
                    ListAdapter adapter = new CustomListAdapter(friendsFragmentContext, friends, CustomListAdapter.FRIENDS_SCREEN);
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
