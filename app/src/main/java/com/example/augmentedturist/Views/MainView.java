package com.example.augmentedturist.Views;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.augmentedturist.Data.InterestPoint;
import com.example.augmentedturist.R;
import com.example.augmentedturist.Views.overlayView.OverlayView;
import com.example.augmentedturist.model.MainViewModel;
import com.example.augmentedturist.presenter.MainActivityContract;
import com.example.augmentedturist.presenter.MainViewPresenter;

import java.util.Hashtable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainView extends Activity implements MainActivityContract.MainActivityViewImpl {

    private final int LOCATION_PERMISSION = 1;
    private final int CAMERA_PERMISSION = 2;
    @BindView(R.id.mainlayout)
    RelativeLayout mainLayout;
    @BindView(R.id.camera_preview)
    FrameLayout previewHolder;
    @BindView(R.id.debug)
    TextView tv;

    private MainViewPresenter mainViewPresenter;

    private CameraPreview cameraPreview;


    private Hashtable<String, OverlayView> floatingViewHashtable = new Hashtable<>();


    @Override
    public void addFloatingView(InterestPoint interestPoint) {
        OverlayView view = new OverlayView(this, interestPoint);
        mainLayout.addView(view);
        floatingViewHashtable.put(interestPoint.getNome(), view);

    }

    @Override
    public void removeFloatingView(String viewName) {
        floatingViewHashtable.remove(viewName);
    }

    @Override
    public void doneLoading(String message) {

    }

    @Override
    public void startLoading(String message) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mainViewPresenter = new MainViewPresenter(this, (MainViewModel) getApplication());

        //TODO: Retirar depois
        //DebugMenu debugMenu= new DebugMenu(tv);
        //debugMenu.execute();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION);
            }
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
            } else {

                // Create our Preview view and set it as the content of our activity.
                // cameraPreview = new CameraPreview(this);
                // previewHolder.addView(cameraPreview);

            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                cameraPreview = new CameraPreview(this, getWindowManager().getDefaultDisplay().getRotation());
                mainViewPresenter.registerLocation();
                mainViewPresenter.registerSensors();
                previewHolder.addView(cameraPreview);
            } else {
                finish();
            }
        } else {
            cameraPreview = new CameraPreview(this, getWindowManager().getDefaultDisplay().getRotation());
            mainViewPresenter.registerSensors();
            mainViewPresenter.registerLocation();
            previewHolder.addView(cameraPreview);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                mainViewPresenter.unregisterLocation();
                mainViewPresenter.unregisterSensors();
            } else {
                finish();
            }
        } else {
            mainViewPresenter.unregisterSensors();
            mainViewPresenter.unregisterLocation();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraPreview.releaseCamera();
    }
}
