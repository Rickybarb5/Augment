package com.example.augmentedturist.providers;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by ricky on 27/10/2016.
 */

public class OrientationProvider implements SensorEventListener {


    private static final float ALPHA = 0.1f;
    public static float orientation[] = new float[3];
    private SensorManager sensorManager;
    //private Sensor gyroSensor ;
    private Sensor accelSensor;
    private Sensor compassSensor;
    private float rotation[] = new float[9];
    private float identity[] = new float[9];
    private float[] lastAccelerometer = new float[3];
    private float[] lastCompass = new float[3];

    public OrientationProvider(Context context) {

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        compassSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        //  gyroSensor = sensors.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    public void registerSensor() {
        sensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, compassSensor, SensorManager.SENSOR_DELAY_GAME);
        //sensorManager.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d("Sensor", "Sensors registered");

    }

    public void unregisterSensor() {
        sensorManager.unregisterListener(this);
        Log.d("Sensor", "Sensors unregistered");
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {


        boolean gotRotation = SensorManager.getRotationMatrix(rotation,
                identity, lastAccelerometer, lastCompass);
        switch (event.sensor.getType()) {

            case Sensor.TYPE_ROTATION_VECTOR:

                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                lastCompass = exponentialSmoothing(event.values, lastCompass, ALPHA);
                break;
            case Sensor.TYPE_ACCELEROMETER:
                lastAccelerometer = exponentialSmoothing(event.values, lastAccelerometer, ALPHA);

                break;
        }
        if (gotRotation) {
            float cameraRotation[] = new float[9];
            // remap such that the camera is pointing straight down the Y axis
            SensorManager.remapCoordinateSystem(rotation, SensorManager.AXIS_X,
                    SensorManager.AXIS_Z, cameraRotation);

            // orientation vector
            orientation = new float[3];
            SensorManager.getOrientation(cameraRotation, orientation);

        }

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
