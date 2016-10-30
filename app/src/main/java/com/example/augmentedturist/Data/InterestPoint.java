package com.example.augmentedturist.Data;

import android.graphics.Bitmap;
import android.location.Location;


/**
 * Created by Ricardo Barbosa on 13/09/2015.
 */
public class InterestPoint {

    private String nome = "";
    //Point location
    private Location location = new Location("");
    //Imagem do ponto
    private Bitmap icon;
    //Descrição
    private String descricao = "";

    public InterestPoint(double lat, double lon, String nome) {
        this.nome = nome;
        this.location.setLatitude(lat);
        this.location.setLongitude(lon);
        this.location.setAltitude(1916.5d);
    }

    public String getNome() {
        return nome;
    }

    public Location getLocation() {
        return location;
    }

    public String getDescricao() {
        return descricao;
    }

    public Bitmap getIcon() {
        return icon;
    }
}
