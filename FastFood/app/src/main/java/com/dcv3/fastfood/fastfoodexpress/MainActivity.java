//The MainActivity file manages the switching between all fragments-DJ


package com.dcv3.fastfood.fastfoodexpress;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.dcv3.fastfood.fastfoodexpress.Fragments.CheckoutFragment;
import com.dcv3.fastfood.fastfoodexpress.Fragments.ConfirmationFragment;
import com.dcv3.fastfood.fastfoodexpress.Fragments.CreateAccountFragment;
import com.dcv3.fastfood.fastfoodexpress.Fragments.CustomizationFragment;
import com.dcv3.fastfood.fastfoodexpress.Fragments.ForgotPasswordFragment;
import com.dcv3.fastfood.fastfoodexpress.Fragments.LoginActivityFragment;
import com.dcv3.fastfood.fastfoodexpress.Fragments.OrderSummaryFragment;
import com.dcv3.fastfood.fastfoodexpress.Fragments.PendingActivityFragment;
import com.dcv3.fastfood.fastfoodexpress.Fragments.PendorStartActivityFragment;
import com.dcv3.fastfood.fastfoodexpress.Fragments.RestaurantMenuFragment;
import com.dcv3.fastfood.fastfoodexpress.Fragments.SelectRestaurantFragment;
import com.dcv3.fastfood.fastfoodexpress.ParseObjects.Orders;
import com.dcv3.fastfood.fastfoodexpress.ParseObjects.Tracker;
import com.parse.ParseAnalytics;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;


import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements SelectRestaurantFragment.OnRestSelectedListener, RestaurantMenuFragment.OnFoodItemSelectedListener{
    FragmentManager fm;
    FragmentTransaction ft;
    public String userId;
    ArrayList<String> orderItems = new ArrayList<String>();
    public String[] customization = new String[100];
    public String restaurantId;
    public ParseGeoPoint restLoc;
    double total = 0;
    String restName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        //changes the fragment view to the login screen-DJ
        switchFragment(new LoginActivityFragment());
    }

    public void OnFoodItemSelected(String item, double price, ParseGeoPoint restLocation){
        orderItems.add(item);
        total += price;
        restLoc = new ParseGeoPoint(restLocation);
        Toast.makeText(this, "Location : " + restLoc, Toast.LENGTH_SHORT).show();
    }

    public void onRestaurantSelected(String id, String name){
        restaurantId = id;
        restName = name;
        //Toast.makeText(this, "clicked " + restLatitude, Toast.LENGTH_SHORT).show();
        viewMenu();
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //function used to switch the fragments-DJ
    public void switchFragment(Fragment fr){
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment, fr);
        ft.commit();
    }

    public void setUserId(String id){
        userId = id;
    }

    public void selectRestaurant(View view){
        switchFragment(new SelectRestaurantFragment());
    }

    public void viewMenu(){
        //these lines send the restaurant id from the activity to the fragment
        Bundle bundle=new Bundle();
        bundle.putString("id", restaurantId);
        //set Fragmentclass Arguments
        RestaurantMenuFragment obj=new RestaurantMenuFragment();
        obj.setArguments(bundle);

        switchFragment(obj);

    }

    public void checkOut(View view){
        Bundle bundle=new Bundle();
        bundle.putStringArrayList("items", orderItems);
        bundle.putDouble("total", total);
        //set Fragmentclass Arguments
        CheckoutFragment obj=new CheckoutFragment();
        obj.setArguments(bundle);

        switchFragment(obj);

    }

    public void viewmenu(View view){
        //these lines send the restaurant id from the activity to the fragment
        Bundle bundle=new Bundle();
        bundle.putString("id", restaurantId);
        //set Fragmentclass Arguments
        RestaurantMenuFragment obj=new RestaurantMenuFragment();
        obj.setArguments(bundle);

        switchFragment(obj);

    }

    public void customizeFood(){
        switchFragment(new CustomizationFragment());
    }

    public void orderSummary(View view){

        //these lines send the restaurant id from the activity to the fragment
        Bundle bundle=new Bundle();
        bundle.putStringArrayList("items", orderItems);
        //set Fragmentclass Arguments
        OrderSummaryFragment obj=new OrderSummaryFragment();
        obj.setArguments(bundle);

        switchFragment(obj);

    }

    public void confirmation(View view){
        Orders newOrder = new Orders();
        newOrder.setdetails(userId, orderItems, restaurantId, total, restName);
        newOrder.saveInBackground();

        switchFragment(new ConfirmationFragment());
    }

    public void forgotPassword(){
        switchFragment(new ForgotPasswordFragment());
    }

    public void createAccount(View view){
        switchFragment(new CreateAccountFragment());
    }


    public void logIn(){
        switchFragment(new LoginActivityFragment());
    }

    public void pending(View view){
        //these lines send the restaurant id from the activity to the fragment
        Bundle bundle=new Bundle();
        bundle.putString("id", userId);
        //set Fragmentclass Arguments
        PendingActivityFragment obj=new PendingActivityFragment();
        obj.setArguments(bundle);

        switchFragment(obj);
    }

    public void pendorstart(View view){
        switchFragment(new PendorStartActivityFragment());
    }

    public void pendorstart(){
        switchFragment(new PendorStartActivityFragment());
    }

    public void onTheWay()
    {
        Tracker gps = new Tracker(this, restLoc);
        if(gps.canGetLocation()){

        }
        pendorstart();
    }


    public void onTheWay(View view)
    {
        Tracker gps = new Tracker(this, restLoc);
        if(gps.canGetLocation()){

        }
        pendorstart();
    }
}

