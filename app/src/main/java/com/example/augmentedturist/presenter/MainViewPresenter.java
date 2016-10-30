package com.example.augmentedturist.presenter;

import com.example.augmentedturist.Data.InterestPoint;
import com.example.augmentedturist.model.MainViewModel;

/**
 * Created by ricky on 27/10/2016.
 */

public class MainViewPresenter implements MainActivityContract.MainActivityPresenterImpl {

    private MainActivityContract.MainActivityViewImpl mainActivityViewImpl;
    private MainActivityContract.MainActivityModelImpl mainActivityModelImpl;

    public MainViewPresenter(MainActivityContract.MainActivityViewImpl mainActivityViewImpl, MainViewModel app) {
        this.mainActivityViewImpl = mainActivityViewImpl;
        this.mainActivityModelImpl = app;
        app.setPresenterInterface(this);

    }

    @Override
    public void updateInterestPoints(InterestPoint interestPoint) {

        mainActivityViewImpl.addFloatingView(interestPoint);
    }

    @Override
    public void registerSensors() {
        mainActivityModelImpl.registerSensors();

    }

    @Override
    public void unregisterSensors() {
        mainActivityModelImpl.unregisterSensors();

    }

    @Override
    public void registerLocation() {
        mainActivityModelImpl.registerLocation();
    }

    @Override
    public void unregisterLocation() {
        mainActivityModelImpl.unregisterLocation();
        ;
    }


}
