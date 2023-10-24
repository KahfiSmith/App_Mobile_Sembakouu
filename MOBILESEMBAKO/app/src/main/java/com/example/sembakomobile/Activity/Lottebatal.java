package com.example.sembakomobile.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.example.sembakomobile.R;

public class Lottebatal extends AppCompatActivity {
    private int waktu_loading=2000;
    LottieAnimationView loti;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottebatal);
        loti = findViewById(R.id.lotie_batal);
        loti.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Intent home = new Intent(Lottebatal.this, Dashboard.class);
                startActivity(home);

                finish();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
}