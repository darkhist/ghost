package floatingheads.snapclone.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import floatingheads.snapclone.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotisFragment extends Fragment {


    /**
     * Required default constructor
     * This class in not implemented anymore
     */
    public NotisFragment() {
         //Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notis, container, false);
    }

}
