package com.example.augmentedturist.presenter;

import com.example.augmentedturist.Data.InterestPoint;

/**
 * Created by ricky on 27/10/2016.
 */

public interface MainActivityContract {


    interface MainActivityViewImpl {
        void addFloatingView(InterestPoint interestPoint);
        void removeFloatingView(String name);

        void doneLoading(String message);

        void startLoading(String message);
    }

    interface MainActivityPresenterImpl {


        void updateInterestPoints(InterestPoint interestPoint);

        void registerSensors();
        void unregisterSensors();

        void registerLocation();

        void unregisterLocation();


    }

    interface MainActivityModelImpl {

        void interestPointUpdated();


        void registerSensors();
        void unregisterSensors();

        void registerLocation();

        void unregisterLocation();

    }
}
