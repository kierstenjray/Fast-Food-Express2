package com.dcv3.fastfood.fastfoodexpress.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dcv3.fastfood.fastfoodexpress.R;

/**
 * Created by dezereljones on 11/17/15.
 */
public class ForgotPasswordFragment extends Fragment {
    public ForgotPasswordFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pwreset, container, false);
        return v;
    }
}
