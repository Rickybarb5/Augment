package com.example.augmentedturist.providers;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.augmentedturist.Data.UserData;


/**
 * Created by Ricardo Barbosa on 07/09/2015.
 */
public class LocationProvider implements LocationListener {

    public LocationManager locationManager;


    public LocationProvider() {


    }

    @Override
    public void onLocationChanged(Location location) {
        //Localização
        UserData.mylocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


}
