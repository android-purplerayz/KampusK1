package com.example.android2.kampuskannekt.ParentNoticeFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android2.kampuskannekt.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PNAdminFragment extends Fragment {


    public PNAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pnadmin, container, false);
    }

}
