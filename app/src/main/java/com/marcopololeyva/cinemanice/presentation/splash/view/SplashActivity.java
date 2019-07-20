package com.marcopololeyva.cinemanice.presentation.splash.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.marcopololeyva.cinemanice.presentation.home.view.HomeActivity;
import com.marcopololeyva.cinemanice.R;
import com.marcopololeyva.cinemanice.presentation.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.logo)
    ImageView logo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        showLoading();

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        };
        timer.schedule(timerTask, 3000);

    }



    private void showLoading() {
        runOnUiThread(() -> {
            Animation pulse = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.pulse);
            logo.startAnimation(pulse);
        });
    }

}
