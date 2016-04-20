package com.dcv3.fastfood.fastfoodexpress.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.dcv3.fastfood.fastfoodexpress.R;
import com.parse.FindCallback;

import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;


import com.parse.ParseException;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by dezereljones on 11/17/15.
 */
public class RestaurantMenuFragment extends Fragment{
    ListView listView;
    ParseQueryAdapter<ParseObject> mainAdapter;
    String restId;
    double price;
    OnFoodItemSelectedListener mCallback;
    Number amount;
    DecimalFormat df = new DecimalFormat("#.##");
    ParseGeoPoint restLoc;


    public RestaurantMenuFragment(){

    }

    //this creates an interface to communicate with the mainactivity
    public interface OnFoodItemSelectedListener {
        void OnFoodItemSelected(String item, double price, ParseGeoPoint restLocation);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnFoodItemSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFoodItemSelected");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        restId =getArguments().getString("id");

        //Toast.makeText(getActivity(), "clicked " + restId, Toast.LENGTH_LONG).show();

        // Initialize main ParseQueryAdapter
        mainAdapter = new ParseQueryAdapter<ParseObject>(getActivity(), new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery<ParseObject> create() {
                // query to get menuitems for selected restaurant
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Menu");
                ParseObject obj = ParseObject.createWithoutData("Restaurants", restId);
                restLoc = obj.getParseGeoPoint("Location");
                //Toast.makeText(getActivity(), "clicked " + restLoc, Toast.LENGTH_LONG).show();
                query.whereEqualTo("restaurantNo", obj);

                return query;
            }
        });

        mainAdapter.setTextKey("menuItems");

        listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(mainAdapter);
        mainAdapter.loadObjects();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                //Getting the name of each item selected
                ParseObject obj = mainAdapter.getItem(position);
                String foodItem = obj.getString("menuItems");
                //Getting the price for each item selected
                amount = obj.getNumber("Price");
                String priceString = df.format(amount.doubleValue());
                price = Double.parseDouble(priceString);

                //Toast.makeText(getActivity(), "clicked " + price, Toast.LENGTH_LONG).show();
/*
                //Creating the instance of PopupMenu
                final PopupMenu popup = new PopupMenu(getActivity(), listView);


                ParseQuery<ParseObject> query = ParseQuery.getQuery("Customize");
                ParseObject object = ParseObject.createWithoutData("Menu", obj.getObjectId());
                query.whereEqualTo("menuId", object);

                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> customization, ParseException e) {
                        if (e == null) {

                            for (int i = 0; i < customization.size(); i++) {
                                popup.getMenu().add(customization.get(i).toString());
                            }

                        } else {

                        }
                    }
                });

                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                /////popup code ends here

*/
                mCallback.OnFoodItemSelected(foodItem, price, restLoc);
            }
        });


        return rootView;
    }
}
