package com.example.augmentedturist.presenter;

import android.content.Context;

/**
 * Created by ricky on 27/10/2016.
 */

public interface SensorContract {

    interface SensorManagementImpl {
        void registerSensor(Context context);

        void unregisterSensor(Context context);
    }
}
