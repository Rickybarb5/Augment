package com.example.augmentedturist.model;

import android.app.Application;

import com.example.augmentedturist.Data.InterestPoint;
import com.example.augmentedturist.Data.UserData;
import com.example.augmentedturist.presenter.MainActivityContract;
import com.example.augmentedturist.providers.LocationProvider;
import com.example.augmentedturist.providers.OrientationProvider;

import java.util.ArrayList;

/**
 * Created by Ricardo Barbosa on 13/09/2015.
 */
public class MainViewModel extends Application implements MainActivityContract.MainActivityModelImpl {
    public static UserData userData = new UserData();
    private static ArrayList<InterestPoint> points = new ArrayList<>();
    private LocationProvider locationProvider;
    private OrientationProvider orientationProvider;

    private MainActivityContract.MainActivityPresenterImpl mainViewPresenterImpl;

    public MainViewModel() {
        super.onCreate();

        //TODO:remove this
        MainViewModel.points.add(new InterestPoint(40.186633, -8.416045, "Jardim"));
        MainViewModel.points.add(new InterestPoint(40.207825, -8.426457, "Torre Coimbra"));

    }


    @Override
    public void interestPointUpdated() {

    }


    @Override
    public void registerSensors() {


        orientationProvider = new OrientationProvider(this);
        orientationProvider.registerSensor();
        //TODO:REMOVE THIS
        mainViewPresenterImpl.updateInterestPoints(MainViewModel.points.get(1));

    }

    @Override
    public void registerLocation() {
        locationProvider = new LocationProvider(this);
        locationProvider.registerSensor();

    }

    @Override
    public void unregisterSensors() {
        orientationProvider.unregisterSensor();
    }


    @Override
    public void unregisterLocation() {

        locationProvider.unregisterSensor();
    }

    public void setPresenterInterface(MainActivityContract.MainActivityPresenterImpl mainActivityPresenter) {
        this.mainViewPresenterImpl = mainActivityPresenter;
    }
}
