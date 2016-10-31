package com.example.augmentedturist.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.example.augmentedturist.Data.InterestPoint;
import com.example.augmentedturist.Data.UserData;
import com.example.augmentedturist.R;
import com.example.augmentedturist.providers.OrientationProvider;

/**
 * Created by Ricardo Barbosa Barbosa on 13/09/2015.
 */
public class OverlayView extends View {


    private static double currentDegreesHorizontal;
    private static double currentDegreesVertical;
    private final float verticalFOV;
    private final float horizontalFOV;
    float curBearingTo;
    private Bitmap bitmap;

    private Paint paint = new Paint();
    private float dx;
    private float dy;
    private int bitmapX, bitmapY;

    private InterestPoint interestPoint;

    public OverlayView(Context context, InterestPoint interestPoint) {
        super(context);
        this.interestPoint = interestPoint;
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.marker);

        verticalFOV = CameraPreview.getverticalFOV();
        horizontalFOV = CameraPreview.getHorizontalFOV();
        paint.setColor(Color.WHITE);
        paint.setTextSize(20);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.LEFT);


    }

    protected void onDraw(Canvas canvas) {
        curBearingTo = UserData.mylocation.bearingTo(interestPoint.getLocation());


        //canvas.drawBitmap(bitmap, x, y, paint);

        if (OrientationProvider.orientation[0] >= 0 && OrientationProvider.orientation[0] <= 180) {
            currentDegreesHorizontal = Math.toDegrees(OrientationProvider.orientation[0]);
        } else {
            currentDegreesHorizontal = Math.toDegrees(OrientationProvider.orientation[0]) + 360;
        }

        currentDegreesVertical = Math.toDegrees(OrientationProvider.orientation[1]) + 45;

        // use roll for screen rotation
        canvas.rotate((float) (0.0f - Math.toDegrees(OrientationProvider.orientation[2])));
        // Translate, but normalize for the FOV of the camera -- basically, pixels per degree, times degrees == pixels
        dx = (float) ((canvas.getWidth() / horizontalFOV) * (currentDegreesHorizontal - curBearingTo));
        dy = (float) ((canvas.getHeight() / verticalFOV) * currentDegreesVertical);

        bitmapX = canvas.getWidth() / 2 - bitmap.getWidth() / 2;
        bitmapY = canvas.getHeight() / 2 - bitmap.getHeight() / 2;


        // wait to translate the dx so the horizon doesn't get pushed off
        canvas.translate(0.0f, 0.0f - dy);
        // make our line big enough to draw regardless of rotation and translation
        canvas.drawLine(0f - canvas.getHeight(), canvas.getHeight() / 2, canvas.getWidth() + canvas.getHeight(), canvas.getHeight() / 2, paint);
        // now translate the dx
        canvas.translate(0.0f - dx, 0.0f);
        // draw our point -- we've rotated and translated this to the right spot already
        canvas.drawBitmap(bitmap, bitmapX, bitmapY, paint);
        // canvas.drawCircle(canvas.getWidth()/2, canvas.getHeight()/2,5f, paint);
        drawText(canvas);
        invalidate();
    }

    private void drawText(Canvas canvas) {
        canvas.drawText((int) UserData.mylocation.distanceTo(interestPoint.getLocation()) + "m", canvas.getWidth() / 2, canvas.getHeight() / 2 - 5, paint);
        canvas.drawText(interestPoint.getNome(), canvas.getWidth() / 2, canvas.getHeight() / 2 - 25, paint);
    }






}
