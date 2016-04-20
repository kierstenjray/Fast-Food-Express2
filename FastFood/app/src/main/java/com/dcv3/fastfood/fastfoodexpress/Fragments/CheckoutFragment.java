package com.dcv3.fastfood.fastfoodexpress.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dcv3.fastfood.fastfoodexpress.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by dezereljones on 4/10/16.
 */
public class CheckoutFragment extends Fragment{
    TextView totalTextView;
    double total;
    double tax;
    ArrayList<String> menuItems;
    DecimalFormat df = new DecimalFormat("#.##");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_checkout, container, false);
        //Retrieving items and order total before tax
        menuItems =getArguments().getStringArrayList("items");
        total = getArguments().getDouble("total");

        //Computing order total after tax
        tax = total * .08;
        total += tax;

        //Formatting total to 2 decimal places
        String priceString = df.format(total);
        total = Double.parseDouble(priceString);

        totalTextView = (TextView)rootView.findViewById(R.id.totalText);

        totalTextView.setText("Total:       $" + total);
        return rootView;
    }

}


