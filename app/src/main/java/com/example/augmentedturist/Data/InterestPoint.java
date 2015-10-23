package com.example.augmentedturist.Data;

import android.graphics.Bitmap;
import android.location.Location;

import com.example.augmentedturist.Threads.GetAltitude;

/**
 * Created by Ricardo Barbosa on 13/09/2015.
 */
public class InterestPoint {

    public String nome = "";
    //Point location
    public Location location = new Location("");
    //Bearing em relação ao user
    public double bearingFromUser = 0;
    //Distancia em relação ao user
    public double distanceFromUser = 0;
    //Imagem do ponto
    public Bitmap icon;
    //Descrição
    public String descricao = "";

    public InterestPoint(double lat, double lon, String nome) {
        this.nome = nome;
        this.location.setLatitude(lat);
        this.location.setLongitude(lon);
        Thread t = new Thread(new GetAltitude(this));
        t.start();
    }
}
