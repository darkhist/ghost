package floatingheads.snapclone.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import floatingheads.snapclone.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View profileView = inflater.inflate(R.layout.fragment_profile, container, false);

//        LinearLayout infoParentContainer = profileView.findViewById(R.id.infoParentContainer);
//        LinearLayout friendsContainer = profileView.findViewById(R.id.friendsContainer);
//        LinearLayout pointsContainer = profileView.findViewById(R.id.pointsContainer);
//
//        int width = infoParentContainer.getWidth();

        return profileView;
    }

}
