package com.example.augmentedturist.presenter;

/**
 * Created by ricky on 27/10/2016.
 */

public interface MainActivityContract {

    interface MainActivityViewImpl {
        void refreshInterestPoints();
    }

    interface MainActivityPresenterImpl {
        void updateInterestPoints();

        void registerSensors();

        void unregisterSensors();
    }

    interface MainActivityModelImpl {
        void registerSensors();

        void unregisterSensors();
    }
}
