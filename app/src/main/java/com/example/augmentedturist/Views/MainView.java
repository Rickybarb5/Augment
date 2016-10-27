package com.example.augmentedturist.Views;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.augmentedturist.Data.InterestPoint;
import com.example.augmentedturist.Data.MainViewModel;
import com.example.augmentedturist.R;
import com.example.augmentedturist.presenter.MainActivityContract;
import com.example.augmentedturist.presenter.MainViewPresenter;
import com.example.augmentedturist.providers.CameraProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainView extends Activity implements MainActivityContract.MainActivityViewImpl {

    @BindView(R.id.mainlayout)
    RelativeLayout mainLayout;
    @BindView(R.id.camera_preview)
    FrameLayout cameraPreview;

    MainViewPresenter mainViewPresenter;
    private CameraProvider cameraProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mainViewPresenter = new MainViewPresenter(this);
        cameraProvider = new CameraProvider(this, cameraPreview);

        MainViewModel.points.add(new InterestPoint(40.186633, -8.416045, "Jardim"));
        MainViewModel.points.add(new InterestPoint(40.207825, -8.426457, "Torre Coimbra"));
    }


    @Override
    protected void onResume() {
        super.onResume();
        mainViewPresenter.registerSensors();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mainViewPresenter.unregisterSensors();
    }


    @Override
    public void refreshInterestPoints() {

        //Temporario
        FloatingView view = new FloatingView(this, MainViewModel.points.get(0));
        FloatingView view2 = new FloatingView(this, MainViewModel.points.get(1));
        mainLayout.addView(view);
        mainLayout.addView(view2);


    }


}
