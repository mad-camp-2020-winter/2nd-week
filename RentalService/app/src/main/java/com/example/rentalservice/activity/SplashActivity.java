package com.example.rentalservice.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.rentalservice.R;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Animation mAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate); // 선글라스
        mAnim.setInterpolator(getApplicationContext(),android.R.anim.accelerate_interpolator);

        ImageView imageView = findViewById(R.id.splash_gif);
        Glide.with(this).load(R.raw.splash_gif).into(imageView);
        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.splash_music);
        mediaPlayer.start();

        imageView.startAnimation(mAnim);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), SelectLoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
