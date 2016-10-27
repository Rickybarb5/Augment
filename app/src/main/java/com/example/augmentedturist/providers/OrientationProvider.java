package com.example.augmentedturist.providers;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.augmentedturist.Data.MainViewModel;

/**
 * Created by ricky on 27/10/2016.
 */

public class OrientationProvider implements SensorEventListener {


    public SensorManager sensorManager;
    public Sensor orientation;
    float[] output = new float[3];
    float[] values;


    public OrientationProvider() {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {

        //Melhora os resultados dos sensores
        output = exponentialSmoothing(event.values, output, Float.parseFloat("0.2"));
//output=event.values;
        //Atribui orientação
        MainViewModel.userData.truehorizontalorientation = Math.round(output[0]);
        MainViewModel.userData.trueverticalorientation = Math.round(output[1]);
        //Transforma a orientação de Portrait para landscape
        double transformedh = MainViewModel.userData.truehorizontalorientation + 90;
        if (transformedh > 360) {
            transformedh = Math.abs(360 - transformedh);
        }
        MainViewModel.userData.convertedhorizontalorientation = transformedh;
        double transformedv = MainViewModel.userData.trueverticalorientation + 90;
        if (transformedv > 360) {
            transformedv = Math.abs(360 - transformedv);
        }
        MainViewModel.userData.convertedverticalorientation = transformedv;
    }

    private float[] exponentialSmoothing(float[] input, float[] output, float alpha) {
        if (output == null)
            return input;
        for (int i = 0; i < input.length; i++) {
            output[i] = output[i] + alpha * (input[i] - output[i]);
        }
        return output;
    }

    public void registerSensor(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        orientation = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }

    public void unregisterSensor() {
        sensorManager.unregisterListener(this);
    }
}
