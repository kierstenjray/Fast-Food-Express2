package com.dcv3.fastfood.fastfoodexpress.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dcv3.fastfood.fastfoodexpress.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dezereljones on 11/17/15.
 */
public class OrderSummaryFragment extends Fragment {

    ArrayList<String> menuItems;

    public OrderSummaryFragment(){

    }

    /*
        ....the summary page is in scroll view
    */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ordersummary, container, false);

        menuItems =getArguments().getStringArrayList("items");

        //Toast.makeText(getActivity(), menuItems.size().t, Toast.LENGTH_LONG).show();

        final ListView listview = (ListView)rootView.findViewById(R.id.list);


        final StableArrayAdapter adapter = new StableArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1, menuItems);
        listview.setAdapter(adapter);


        return rootView;
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }


}
