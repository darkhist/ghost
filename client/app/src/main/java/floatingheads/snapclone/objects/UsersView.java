package floatingheads.snapclone.objects;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by root on 2/26/18.
 */

public class UsersView extends ListView {

    private Context context;
    private ArrayList<User> usersArrayList;

    public UsersView(Context context) {
        super(context);
    }

    public UsersView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public UsersView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public void setContents(ArrayList<User> contents) {
        usersArrayList = contents;
        ListAdapter la = new CustomListAdapter(context, usersArrayList, CustomListAdapter.USERS_SCREEN);
        setAdapter(la);
    }

    public ArrayList<User> getUsers() {
        return usersArrayList;
    }

    public void init() {
        setOnItemClickListener(
                (AdapterView<?> parent, View view, int position, long id) -> {
                    VolleyActions va = new VolleyActions(context);
                    va.makeJSONArrayRequest("http://proj-309-vc-4.cs.iastate.edu:3000/users", this);
                }
        );
    }
}
