package com.example.reduxsample.modules.user;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JustShowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JustShowFragment extends Fragment {
    public JustShowFragment() {
        // Required empty public constructor
    }


    public static JustShowFragment newInstance() {
        JustShowFragment fragment = new JustShowFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_just_show, container, false);
    }

}
