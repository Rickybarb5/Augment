package com.example.augmentedturist.Engines;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.augmentedturist.Data.Data;
import com.example.augmentedturist.Threads.GetAltitude;


/**
 * Created by Ricardo Barbosa on 07/09/2015.
 */
public class LocationEngine implements LocationListener, SensorEventListener {

    public LocationManager locationManager;

    public SensorManager sensorManager;
    public Sensor orientation;
    float[] output = new float[3];
    float[] values;

    public LocationEngine() {

        //TODO: tirar isto depois
        Data.mylocation.setLatitude(40.186646);
        Data.mylocation.setLongitude(-8.415646);
        Thread t = new Thread(new GetAltitude(Data.mylocation));
        t.start();
        Data.updateValues();
    }

    @Override
    public void onLocationChanged(Location location) {
        //Localização
        Data.mylocation = location;
        Data.updateValues();
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

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


    public void onSensorChanged(SensorEvent event) {

        //Melhora os resultados dos sensores
        output = exponentialSmoothing(event.values, output, Float.parseFloat("0.2"));
//output=event.values;
        //Atribui orientação
        Data.truehorizontalorientation = Math.round(output[0]);
        Data.trueverticalorientation = Math.round(output[1]);
        //Transforma a orientação de Portrait para landscape
        double transformedh = Data.truehorizontalorientation + 90;
        if (transformedh > 360) {
            transformedh = Math.abs(360 - transformedh);
        }
        Data.convertedhorizontalorientation = transformedh;
        double transformedv = Data.trueverticalorientation + 90;
        if (transformedv > 360) {
            transformedv = Math.abs(360 - transformedv);
        }
        Data.convertedverticalorientation = transformedv;
    }


    private float[] exponentialSmoothing(float[] input, float[] output, float alpha) {
        if (output == null)
            return input;
        for (int i = 0; i < input.length; i++) {
            output[i] = output[i] + alpha * (input[i] - output[i]);
        }
        return output;
    }

}
