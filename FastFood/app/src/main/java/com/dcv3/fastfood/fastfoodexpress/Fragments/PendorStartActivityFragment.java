package com.dcv3.fastfood.fastfoodexpress.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dcv3.fastfood.fastfoodexpress.R;

/**
 * Created by dezereljones on 11/28/15.
 */
public class PendorStartActivityFragment extends Fragment{

    public PendorStartActivityFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pendorstart, container, false);
        return v;
    }


}
