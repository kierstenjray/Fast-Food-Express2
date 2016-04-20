package com.dcv3.fastfood.fastfoodexpress.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dcv3.fastfood.fastfoodexpress.R;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

/**
 * Created by dezereljones on 11/28/15.
 */
public class PendingActivityFragment extends Fragment{
    ParseQueryAdapter<ParseObject> mainAdapter;
    ListView listView;
    String userId;
    public PendingActivityFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pending, container, false);

        userId =getArguments().getString("id");

        // query pulls only the current users orders
        // Initialize main ParseQueryAdapter
        mainAdapter = new ParseQueryAdapter<ParseObject>(getActivity(), new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery<ParseObject> create() {
                // query to get orders for selected user
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Orders");
                ParseObject obj = ParseObject.createWithoutData(ParseUser.class, userId);
                //Toast.makeText(getActivity(), "clicked " + restLoc, Toast.LENGTH_LONG).show();
                query.whereEqualTo("userID", obj);

                return query;
            }
        });

        mainAdapter.setTextKey("restaurantName");


        listView = (ListView) v.findViewById(R.id.list);
        listView.setAdapter(mainAdapter);
        mainAdapter.loadObjects();

        return v;
    }


}
