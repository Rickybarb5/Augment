package com.example.augmentedturist;

import android.os.AsyncTask;
import android.widget.TextView;

import com.example.augmentedturist.Data.Data;
import com.example.augmentedturist.Engines.LocationEngine;


/**
 * Created by Ricardo Barbosa on 07/09/2015.
 */
public class DebugMenu extends AsyncTask<Void, Void, Void> {

    TextView lat, lon, speed, altitude;
    LocationEngine locationEngine;

    public DebugMenu(TextView lat, TextView lon, TextView speed, TextView altitude,
                     LocationEngine locationEngine) {
        this.lat = lat;
        this.lon = lon;
        this.speed = speed;
        this.altitude = altitude;
        this.locationEngine = locationEngine;
    }

    @Override
    protected Void doInBackground(Void... params) {


        while (true) {

            publishProgress();


        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        try {
            lat.setText("Lat: " + Data.mylocation.getLatitude());
            lon.setText("Lon: " + Data.mylocation.getLongitude());
            speed.setText("V: " + Data.mylocation.getSpeed());
            altitude.setText("A: " + Data.mylocation.getAltitude());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
