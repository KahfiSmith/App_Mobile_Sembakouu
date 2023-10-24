package com.example.sembakomobile.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sembakomobile.R;

public class register_berhasil extends AppCompatActivity {
    private int waktu_loading=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_berhasil);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent home = new Intent(register_berhasil.this, login.class);
                startActivity(home);
                finish();

            }
        },waktu_loading);
    }
}
