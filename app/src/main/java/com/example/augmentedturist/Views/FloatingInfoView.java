package com.example.augmentedturist.Views;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.augmentedturist.Data.InterestPoint;
import com.example.augmentedturist.R;

/**
 * Created by Ricardo Barbosa on 29/09/2015.
 */
public class FloatingInfoView extends View {

    InterestPoint ip;
    TextView t, d;
    Integer x = 0, y = 0;

    public FloatingInfoView(Context context, InterestPoint ip, Integer fvx, Integer fvy) {
        super(context);
        this.ip = ip;
        View v = View.inflate(context, R.layout.floatinginfoview, null);
        t = (TextView) v.findViewById(R.id.titulo);
        d = (TextView) v.findViewById(R.id.descricao);
        t.setText(ip.nome);
        d.setText(ip.descricao);

        x = fvx;
        y = fvy;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(x + 20, y + 20);
        this.setLayoutParams(layoutParams);
    }


}
