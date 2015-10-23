package com.example.augmentedturist.Threads;

import android.location.Location;

import com.example.augmentedturist.Data.InterestPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Ricardo Barbosa on 28/09/2015.
 */
public class GetAltitude implements Runnable {

    double lat, lon;
    InterestPoint ip;
    Location location;

    public GetAltitude(Location location) {
        this.lat = location.getLatitude();
        this.lon = location.getLongitude();
        this.location = location;
    }

    public GetAltitude(InterestPoint ip) {
        this.lat = ip.location.getLatitude();
        this.lon = ip.location.getLongitude();
        this.ip = ip;
    }

    @Override
    public void run() {

        if (lat != 0 && lon != 0) {
            try {
                URL yahoo = new URL("http://maps.googleapis.com/maps/api/elevation/json?locations=" + lat + "," + lon + "&sensor=true");
                URLConnection yc = yahoo.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        yc.getInputStream(), "UTF-8"));
                String inputLine;
                StringBuilder a = new StringBuilder();
                while ((inputLine = in.readLine()) != null)
                    a.append(inputLine);
                in.close();

       /* URL url ;

            url = new URL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);*/
                JSONObject json = new JSONObject(a.toString());
                JSONArray array = json.getJSONArray("results");
                JSONObject otherjson = array.getJSONObject(0);
                double elevation = (double) otherjson.get("elevation");

                if (ip != null)
                    ip.location.setAltitude(elevation);

                if (location != null) {
                    location.setAltitude(elevation);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                System.out.println();
            }
        }

    }


}
