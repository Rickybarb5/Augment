package com.example.augmentedturist.Data;

import android.app.Application;
import android.location.Location;

import java.util.ArrayList;

/**
 * Created by Ricardo Barbosa on 13/09/2015.
 */
public class Data extends Application {
    public static ArrayList<InterestPoint> points = new ArrayList<>();
    //Localização do user
    public static Location mylocation = new Location("");
    //orientação do telemovel
    public static double truehorizontalorientation = 0;
    public static double trueverticalorientation = 0;
    //orientação do user
    public static double convertedhorizontalorientation = 0;
    public static double convertedverticalorientation = 0;

    //Thread de update de valores
    public static Thread t = new Thread();

    //updates bearing and distance from user for all points
    public static void updateValues() {

        if (!t.isAlive()) {
            t = new Thread(new UpdateValues());
            t.start();
        }
    }

    public static class UpdateValues implements Runnable {


        @Override
        public void run() {


            while (true) {
                for (InterestPoint p : points) {
                    p.bearingFromUser = mylocation.bearingTo(p.location);
                    p.distanceFromUser = mylocation.distanceTo(p.location);
                }
            }

        }
    }
}
