package com.laquerrehugo.app.ay.views;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.laquerrehugo.app.ay.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ThanksActivity extends Activity {

    //Views
    @BindView(R.id.thanks) TextView Thanks;

    //Initialize
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);

        bind();
        init();
    }
    private void bind() {
        ButterKnife.bind(this);
    }
    private void init() {
        //Load fonts for the logo
        AssetManager assets = getApplicationContext().getAssets();
        Typeface coquette = Typeface.createFromAsset(assets, "fonts/Coquette Bold.ttf");

        //Set the fonts
        Thanks.setTypeface(coquette);
    }
}
