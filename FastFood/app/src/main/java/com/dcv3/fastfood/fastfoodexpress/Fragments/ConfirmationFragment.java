//This class inflates the confirmation fragment.  The confirmation fragment shows the user
// a confirmation screen after they order and pay for their food -DJ

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
public class ConfirmationFragment extends Fragment {
    public ConfirmationFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_confirmation, container, false);
        return v;
    }
}
