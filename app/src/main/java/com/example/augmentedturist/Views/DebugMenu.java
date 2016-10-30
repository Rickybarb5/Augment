package com.example.augmentedturist.Views;

/**
 * Created by ricky on 27/10/2016.
 */


import android.os.AsyncTask;
import android.widget.TextView;

import com.example.augmentedturist.providers.OrientationProvider;


/**
 * Created by Ricardo Barbosa on 07/09/2015.
 */
class DebugMenu extends AsyncTask<Void, Void, Void> {

    private TextView tv;

    public DebugMenu(TextView tv) {
        this.tv = tv;
    }

    @Override
    protected Void doInBackground(Void... params) {


        while (true) {

            synchronized (this) {
                try {
                    wait(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            publishProgress();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        try {

            tv.setText("ORIENTATION:\nX: " + OrientationProvider.orientation[0] + "\n" + "Y: "
                    + OrientationProvider.orientation[1] + "\nZ: " + OrientationProvider.orientation[2]);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
