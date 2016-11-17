package com.example.augmentedturist.Views.overlayView;

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
import com.example.augmentedturist.Views.CameraPreview;
import com.example.augmentedturist.providers.OrientationProvider;

/**
 * Created by Ricardo Barbosa Barbosa on 13/09/2015.
 */
public class OverlayView extends View {

    private static double currentDegreesHorizontal;
    private static double currentDegreesVertical;
    protected Paint paint = new Paint();
    InterestPoint interestPoint;
    private float curBearingTo;
    private float dx;
    private float dy;
    private float verticalFOV;
    private float horizontalFOV;
    private Bitmap bitmap;

    public OverlayView(Context context, InterestPoint interestPoint) {
        super(context);
        this.interestPoint = interestPoint;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker);
        verticalFOV = CameraPreview.getverticalFOV();
        horizontalFOV = CameraPreview.getHorizontalFOV();
        paint.setColor(Color.WHITE);

    }

    @Override
    public void onDraw(Canvas canvas) {


        calculateCoordinates(canvas);
        drawBitmap(canvas);
        invalidate();

    }

    private void drawBitmap(Canvas canvas) {
        canvas.rotate((float) (0.0f - Math.toDegrees(OrientationProvider.orientation[2])));
        // wait to translate the dx so the horizon doesn't get pushed off
        canvas.translate(0.0f, 0.0f - dy);
        // make our line big enough to draw regardless of rotation and translation
        canvas.drawLine(0f - canvas.getHeight(), canvas.getHeight() / 2, canvas.getWidth() + canvas.getHeight(), canvas.getHeight() / 2, paint);
        // now translate the dx
        canvas.translate(0.0f - dx, 0.0f);
        // draw our point -- we've rotated and translated this to the right spot already
        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, 8.0f, paint);
        canvas.drawBitmap(bitmap, canvas.getWidth() / 2 - bitmap.getHeight() / 2, canvas.getHeight() / 2 - bitmap.getWidth() / 2, paint);
    }

    private void calculateCoordinates(Canvas canvas) {

        curBearingTo = UserData.mylocation.bearingTo(interestPoint.getLocation());
        if (OrientationProvider.orientation[0] >= 0 && OrientationProvider.orientation[0] <= 180) {
            currentDegreesHorizontal = Math.toDegrees(OrientationProvider.orientation[0]);
        } else {
            currentDegreesHorizontal = Math.toDegrees(OrientationProvider.orientation[0]) + 360;
        }
        currentDegreesVertical = Math.toDegrees(OrientationProvider.orientation[1]) + 45;
        // use roll for screen rotation

        // Translate, but normalize for the FOV of the camera -- basically, pixels per degree, times degrees == pixels
        dx = (float) ((canvas.getWidth() / horizontalFOV) * (currentDegreesHorizontal - curBearingTo));
        dy = (float) ((canvas.getHeight() / verticalFOV) * currentDegreesVertical);

    }

    private void drawText(Canvas canvas) {
        canvas.drawText("Lorem ipsum dolor sit amet, scripta accumsan rationibus no duo, ne novum repudiare duo, eu scripta legendos mel. Mei vide mediocrem cu, ex impedit intellegebat mea. In affert evertitur omittantur est, suas brute tempor no his. At doming iisque aliquando per, no mei mandamus efficiendi. An justo quaestio erroribus cum, et qui salutatus persequeris. Vim vidit delicatissimi at.\n" +
                        "\n" +
                        "Commodo consequat adolescens an eos. Mea nostrum disputando in, ludus feugait explicari ei nam. Eu vis nisl quot laudem. Singulis deseruisse reformidans ne ius.\n" +
                        "\n" +
                        "Ea qui alia virtute scripserit, ad his vitae pericula. Ad autem modus quando per. Velit habemus fuisset eum eu. Vix ignota pertinax accommodare eu, duo ne nibh aliquando repudiandae.\n"
                , canvas.getWidth() / 2, canvas.getHeight() / 2, paint);
    }

}
