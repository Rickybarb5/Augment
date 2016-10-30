package com.example.augmentedturist.Views;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

/**
 * Created by ricky on 30/10/2016.
 */

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private static Camera mCamera;
    private SurfaceHolder mHolder;
    private int rotation;

    public CameraPreview(Context context, int rotation) {
        super(context);
        mCamera = getCameraInstance();
        this.rotation = rotation;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    public static float getverticalFOV() {
        Camera.Parameters params = mCamera.getParameters();
        return params.getVerticalViewAngle();

    }

    public static float getHorizontalFOV() {
        Camera.Parameters params = mCamera.getParameters();
        return params.getHorizontalViewAngle();

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        /*// The Surface has been created, now tell the camera where to draw the preview.
        try {
            mCamera.setPreviewDisplay(holder);
         mCamera.stopPreview();
        } catch (IOException e) {
            Log.d("Camera", "Error setting camera preview: " + e.getMessage());
        }*/
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, info);

        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        mCamera.setDisplayOrientation((info.orientation - degrees + 360) % 360);

        try {
            mCamera.setPreviewDisplay(mHolder);
        } catch (IOException e) {
            Log.e("Camera", "surfaceCreated exception: ", e);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
        mCamera.stopPreview();
        mCamera.release();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
    /*    // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null){
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
         mCamera.stopPreview();
        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
           mCamera.startPreview();

        } catch (Exception e){
            Log.d("Camera", "Error starting camera preview: " + e.getMessage());
        }*/
        Camera.Parameters params = mCamera.getParameters();
        List<Camera.Size> prevSizes = params.getSupportedPreviewSizes();
        for (Camera.Size s : prevSizes) {
            if ((s.height <= h) && (s.width <= w)) {
                params.setPreviewSize(s.width, s.height);
                break;
            }
        }

        mCamera.setParameters(params);
        mCamera.startPreview();
    }
}