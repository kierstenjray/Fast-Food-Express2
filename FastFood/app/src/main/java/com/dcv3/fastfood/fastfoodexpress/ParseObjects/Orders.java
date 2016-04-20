package com.dcv3.fastfood.fastfoodexpress.ParseObjects;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by dezereljones on 1/16/16.
 */
@ParseClassName("Orders")
public class Orders extends ParseObject {


    public Orders(){
        super();
    }

    //Method saves the order to the database
    public void setdetails(String id, ArrayList<String> items, String property, double cost, String name){
        String[] menuItems = new String[(items.size())];
        items.toArray(menuItems);
        //put("restaurantNo", property);
        //put("userID", id);
        put("userID", ParseObject.createWithoutData(ParseUser.class, id));
        put("restaurantNo", ParseObject.createWithoutData("Restaurants", property));
        put("Total", cost);
        put("restaurantName", name);
        for (int i = 0; i < menuItems.length; i++)
            add("menuItems", menuItems[i]);

    }
}
