package com.example.augmentedturist.presenter;

import com.example.augmentedturist.Data.MainViewModel;

/**
 * Created by ricky on 27/10/2016.
 */

public class MainViewPresenter implements MainActivityContract.MainActivityPresenterImpl {

    MainActivityContract.MainActivityViewImpl mainActivityView;
    MainActivityContract.MainActivityModelImpl mainActivityModel;

    public MainViewPresenter(MainActivityContract.MainActivityViewImpl mainActivityView) {
        this.mainActivityView = mainActivityView;
        this.mainActivityModel = new MainViewModel(this);
    }

    @Override
    public void updateInterestPoints() {

    }

    @Override
    public void registerSensors() {
        mainActivityModel.registerSensors();

    }

    @Override
    public void unregisterSensors() {
        mainActivityModel.unregisterSensors();

    }
}
