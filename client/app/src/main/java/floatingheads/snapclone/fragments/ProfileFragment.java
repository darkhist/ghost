package floatingheads.snapclone.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import floatingheads.snapclone.R;
import floatingheads.snapclone.activities.CameraPreviewActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private Button backToCameraBtn;

    /**
     * Required default constructor
     */
    public ProfileFragment() {
        //
    }

    /**
     * Returns View containing user's profile
     * User's profile displays information about the user like their name, friends, and points
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
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

        TextView name = profileView.findViewById(R.id.profileUsername);

//        getArguments().getInt("uid");
        String first = getArguments().getString("firstName");
        String last = getArguments().getString("lastName");
//        getArguments().getString("username");
//        getArguments().getString("email");

        String fullName = first + " " + last;

        name.setText(fullName);

        backToCameraBtn = (Button) profileView.findViewById(R.id.profile_to_camera);

        backToCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), CameraPreviewActivity.class);
                startActivity(i);
            }
        });

        return profileView;
    }

}
