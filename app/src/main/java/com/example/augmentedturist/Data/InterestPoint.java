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
    private String descricao = "Lorem ipsum dolor sit amet, scripta accumsan rationibus no duo, ne novum repudiare duo, eu scripta legendos mel. Mei vide mediocrem cu, ex impedit intellegebat mea. In affert evertitur omittantur est, suas brute tempor no his. At doming iisque aliquando per, no mei mandamus efficiendi. An justo quaestio erroribus cum, et qui salutatus persequeris. Vim vidit delicatissimi at.\n" +
            "\n" +
            "Commodo consequat adolescens an eos. Mea nostrum disputando in, ludus feugait explicari ei nam. Eu vis nisl quot laudem. Singulis deseruisse reformidans ne ius.\n" +
            "\n" +
            "Ea qui alia virtute scripserit, ad his vitae pericula. Ad autem modus quando per. Velit habemus fuisset eum eu. Vix ignota pertinax accommodare eu, duo ne nibh aliquando repudiandae.\n";

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
