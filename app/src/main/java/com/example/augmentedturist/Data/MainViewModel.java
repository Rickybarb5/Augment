package com.example.augmentedturist.Data;

import android.app.Application;

import com.example.augmentedturist.presenter.MainActivityContract;
import com.example.augmentedturist.presenter.MainViewPresenter;
import com.example.augmentedturist.providers.LocationProvider;
import com.example.augmentedturist.providers.OrientationProvider;

import java.util.ArrayList;

/**
 * Created by Ricardo Barbosa on 13/09/2015.
 */
public class MainViewModel extends Application implements MainActivityContract.MainActivityModelImpl {
    public static ArrayList<InterestPoint> points = new ArrayList<>();
    public static UserData userData = new UserData();

    private LocationProvider locationProvider;
    private OrientationProvider orientationProvider;
    private MainViewPresenter mainViewPresenter;

    public MainViewModel(MainViewPresenter mainViewPresenter) {
        this.mainViewPresenter = mainViewPresenter;
        locationProvider = new LocationProvider();
        orientationProvider = new OrientationProvider();
    }

    @Override
    public void registerSensors() {
        orientationProvider.registerSensor(this);
    }

    @Override
    public void unregisterSensors() {
        orientationProvider.unregisterSensor();
    }

}
