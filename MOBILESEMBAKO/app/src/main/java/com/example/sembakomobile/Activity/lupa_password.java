package com.example.sembakomobile.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.sembakomobile.R;

public class lupa_password extends AppCompatActivity {
    AppCompatButton lupa_password;
    ImageButton bt_back;
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);

        lupa_password = findViewById(R.id.lupa_password);
        bt_back = findViewById(R.id.buttonBack);
        bt_back.setOnClickListener(view -> onBackPressed());

        lupa_password.setOnClickListener(view -> {
            String wpurl = "https://wa.me/+6287704632355?text=Admin, Saya ingin mereset password.";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(wpurl));
            startActivity(intent);
        });
    }
}