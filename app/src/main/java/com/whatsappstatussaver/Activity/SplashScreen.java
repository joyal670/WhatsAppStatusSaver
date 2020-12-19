package com.whatsappstatussaver.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.whatsappstatussaver.R;

public class SplashScreen extends AppCompatActivity
{
    Handler handler;
    ImageView splashImg;
    TextView splashText;
    Animation bottom, above;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorBlue));
        }
        splashImg = findViewById(R.id.splashImg);
        splashText = findViewById(R.id.splashText);

        bottom = AnimationUtils.loadAnimation(this, R.anim.bottom);
        splashImg.setAnimation(bottom);

        above = AnimationUtils.loadAnimation(this, R.anim.above);
        splashText.setAnimation(above);


        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, PermissionActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }
}