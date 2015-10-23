package com.example.augmentedturist.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.example.augmentedturist.Data.Data;
import com.example.augmentedturist.Data.InterestPoint;
import com.example.augmentedturist.R;

/**
 * Created by Ricardo Barbosa Barbosa on 13/09/2015.
 */
public class FloatingView extends View {

    public static final int CAMERA_VISION_RADIUS_HORIZONTAL = 90;
    public static final int CAMERA_VISION_RADIUS_VERTICAL = 30;
    //Posição da view
    public Integer x = 0, y = 0;
    Bitmap bitmap;
    Paint paint = new Paint();
    Thread a;
    //Tamanho do ecra
    int window_w, window_h;
    double orientationFromUser;
    double altitudeFromUser;
    private InterestPoint ip;

    public FloatingView(Context context, InterestPoint ip) {
        super(context);
        this.ip = ip;
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.marker);


    }

    protected void onDraw(Canvas canvas) {
        window_w = canvas.getWidth();
        window_h = canvas.getHeight();

        if (a != null) {
            if (!a.isAlive()) {
                a.start();
            }
        } else {
            a = new Thread(new UpdateValues(canvas));
        }
        //TODO:MUDAR O Y DEPOIS


        //desenha informação de texto
        drawText(canvas);
        //desenha o bitmap
        canvas.drawBitmap(bitmap, x, y, paint);
        //actualiza a posição da view
        invalidate();
    }

    private void drawText(Canvas canvas) {

        paint.setColor(Color.WHITE);
        paint.setTextSize(20);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText((int) ip.distanceFromUser + "m", x, y - 5, paint);
        canvas.drawText(ip.nome, x, y - 25, paint);
    }


    class UpdateValues implements Runnable {
        Canvas c;

        public UpdateValues(Canvas c) {
            this.c = c;
        }

        @Override
        public void run() {

            while (true) {
                setx();
                sety();
                //calcula o bearing do ponto
                setOrientation();
            }
        }

        private void setOrientation() {
            if (ip.bearingFromUser < 0) {
                ip.bearingFromUser = 360 - Math.abs(ip.bearingFromUser);
                orientationFromUser = Data.convertedhorizontalorientation - ip.bearingFromUser;
            } else {
                orientationFromUser = Data.convertedhorizontalorientation - ip.bearingFromUser;
            }
            if (orientationFromUser < 0) {
                orientationFromUser = 360 + orientationFromUser;
            }
            if (orientationFromUser > 360) {
                orientationFromUser = 360 - orientationFromUser;
            }
            // canvas.drawText("OFU: "+(int)orientationFromUser,window_h/2-bitmap.getWidth()/2,y, paint);
        }

        private void sety() {
            if (Data.mylocation.getAltitude() != 0) {
                Data.convertedverticalorientation =

                        (Math.atan((Data.mylocation.getAltitude() - ip.location.getAltitude())//
                                /
                                Math.sqrt((Data.mylocation.getAltitude() - ip.location.getAltitude() * Data.mylocation.getAltitude() - ip.location.getAltitude())
                                        - Data.mylocation.distanceTo(ip.location) * Data.mylocation.distanceTo(ip.location)))) - Data.trueverticalorientation;


                y = (int) Data.convertedverticalorientation * (window_h / 2 - (bitmap.getWidth() / 2)) / 30;
            } else
                y = window_h / 2 - (bitmap.getWidth() / 2);
        }

        private void setx() {
            if (orientationFromUser <= 90) {
                x = (int) (window_w / 2 - (bitmap.getHeight() / 2) - (orientationFromUser * window_h / CAMERA_VISION_RADIUS_HORIZONTAL));
            } else if (orientationFromUser >= 270) {
                x = (int) (window_w / 2 - (bitmap.getHeight() / 2) + ((360 - orientationFromUser) * window_h / CAMERA_VISION_RADIUS_HORIZONTAL));
            } else
                x = (int) (window_w / 2 - (bitmap.getHeight() / 2) - (orientationFromUser * window_h / CAMERA_VISION_RADIUS_HORIZONTAL));


        }
    }
}
