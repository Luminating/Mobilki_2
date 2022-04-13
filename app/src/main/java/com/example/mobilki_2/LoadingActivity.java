package com.example.mobilki_2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class LoadingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        ImageView imageView = findViewById(R.id.imageViewSplash);
        AnimatedVectorDrawable avdSplash = (AnimatedVectorDrawable)imageView.getDrawable();
        avdSplash.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 1500);
        Profiles.getInstance().deserializeObjects(getApplicationContext());
    }


}