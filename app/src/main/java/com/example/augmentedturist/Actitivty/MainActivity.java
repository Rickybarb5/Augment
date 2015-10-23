package com.example.augmentedturist.Actitivty;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.augmentedturist.Data.Data;
import com.example.augmentedturist.Data.InterestPoint;
import com.example.augmentedturist.Engines.CameraEngine;
import com.example.augmentedturist.Engines.LocationEngine;
import com.example.augmentedturist.Engines.VoiceEngine;
import com.example.augmentedturist.R;
import com.example.augmentedturist.Views.FloatingView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int REQ_CODE_SPEECH_INPUT = 100;
    LocationEngine locationEngine;
    DebugMenu debugMenu;
    TextView txtSpeechInput;
    private CameraEngine cameraEngine;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //tirar isto depois
        Data.points.add(new InterestPoint(40.186633, -8.416045, "Jardim"));
        Data.points.add(new InterestPoint(40.207825, -8.426457, "Torre Coimbra"));


        //inicializa engines
        cameraEngine = new CameraEngine(this, (FrameLayout) findViewById(R.id.camera_preview));
        locationEngine = new LocationEngine();
        //inicializa debug menu e adiciona testView
        getViews();

    }


    public void getViews() {
        debugMenu = new DebugMenu(
                (TextView) findViewById(R.id.latitude),
                (TextView) findViewById(R.id.longitude),
                (TextView) findViewById(R.id.velocidade),
                (TextView) findViewById(R.id.altitude),
                locationEngine);
        debugMenu.execute();
        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        RelativeLayout rel = (RelativeLayout) findViewById(R.id.mainlayout);
        FloatingView view = new FloatingView(this, Data.points.get(0));
        FloatingView view2 = new FloatingView(this, Data.points.get(1));
        rel.addView(view);
        rel.addView(view2);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                VoiceEngine.promptSpeechInput();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();


        locationEngine.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        locationEngine.orientation = locationEngine.sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        locationEngine.sensorManager.registerListener(locationEngine, locationEngine.orientation, SensorManager.SENSOR_DELAY_FASTEST);


        locationEngine.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gpsIsEnabled = locationEngine.locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean networkIsEnabled = locationEngine.locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (gpsIsEnabled) {

            locationEngine.locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 1000, 5, locationEngine);
        } else if (networkIsEnabled) {
            locationEngine.locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 1000, 5,
                    locationEngine);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();

        locationEngine.locationManager.removeUpdates(locationEngine);
        Data.t.interrupt();
        System.gc();
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));
                }
                break;
            }

        }
    }

}
