package com.example.augmentedturist.Views;

import android.view.View;
import android.widget.RelativeLayout;

import com.example.augmentedturist.Data.InterestPoint;

/**
 * Created by Ricardo Barbosa on 29/09/2015.
 */
public class FloatingViewClickListener implements View.OnClickListener {

    InterestPoint p;
    Integer x, y;
    RelativeLayout rel;

    public FloatingViewClickListener(InterestPoint ip, Integer x, Integer y, RelativeLayout rel) {
        this.p = ip;
        this.x = x;
        this.y = y;
        this.rel = rel;
    }

    @Override
    public void onClick(View v) {

        FloatingInfoView fiv = new FloatingInfoView(v.getContext(), p, x, y);
        rel.addView(fiv);

    }
}
