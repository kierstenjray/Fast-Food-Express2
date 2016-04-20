//This class tracks the users distance to the restaurant and prints a ticket when the user is within 5 meters of the restaurant

package com.dcv3.fastfood.fastfoodexpress.ParseObjects;


import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by dezereljones on 2/2/16.
 */
public class Tracker extends Service implements LocationListener{
    private final Context mContext;

    // flag for GPS status
    boolean isGPSEnabled = false;
    // flag for network status
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;
    double latitude;
    double longitude;
    Location location;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5; // 5 meter
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 5000; // 5 secs
    // Declaring a Location Manager
    protected LocationManager locationManager;
    public Location restLocation = new Location("Restlocation");
    public boolean arrived = false;

/*
    Context context;
    public boolean arrived = false;
    public double restLatitude;
    public double restLongitude;
    public Location restLocation;
    private LocationManager locationManager;
*/
    //constructor
    public Tracker(Context context, ParseGeoPoint location){
        this.mContext = context;
        convertLocation(location);
        getLocation();
    }

    //converts the ParseGeoPoint to Location
    public void convertLocation(ParseGeoPoint restaurantLoc){
        double restLatitude;
        double restLongitude;
        restLatitude = (restaurantLoc.getLatitude());
        restLongitude = (restaurantLoc.getLongitude());

        restLocation.setLatitude(restLatitude);
        restLocation.setLongitude(restLongitude);
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            }
            else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }


    //Function to get latitude

    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }


    //Function to get longitude
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check if best network provider
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog
     * */
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(Tracker.this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        double dist = location.distanceTo(restLocation);
        if (dist < 300){
            arrived = true;
            Toast.makeText(mContext, "you made it", Toast.LENGTH_LONG).show();

            /////this is where order is sent to restaurant current order screen
            ///also the order number is created
            ///restaurant deletes order from database with delete button
            //stop tracker when arrived at location
            stopUsingGPS();
        }
        else Toast.makeText(mContext, dist + " to location", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

/*
    //method requests the users location every 2 seconds until they arrive at the restaurant
    public void updateLocation(Context c) {
        while(!arrived){
            locationManager = (LocationManager)c.getSystemService(Context.LOCATION_SERVICE);

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    2000,
                    5, this);
        }

    }

    //this method checks the distance between the restaurant and the user if the location of the
    //user changes
    @Override
    public void onLocationChanged(Location location){
        double dist = location.distanceTo(restLocation);
        if (dist < 10){
            arrived = true;
            Toast.makeText(context, "you made it", Toast.LENGTH_SHORT).show();

            /////this is where order is sent to restaurant current order screen
            ///also the order number is created
            ///restaurant deletes order from database with delete button
            //also need to stop tracker
        }
    }

    //converts the ParseGeoPoint to Location
    public void convertLocation(ParseGeoPoint restaurantLoc){
        restLatitude = (restaurantLoc.getLatitude()*1E6);
        restLongitude = (restaurantLoc.getLongitude()*1E6);

        restLocation.setLatitude(restLatitude);
        restLocation.setLongitude(restLongitude);
    }



    @Override
    public void onProviderDisabled(String provider){}

    @Override
    public void onProviderEnabled(String provider){}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras){}
*/
}
