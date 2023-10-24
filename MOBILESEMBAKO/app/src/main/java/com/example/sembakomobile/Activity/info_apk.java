package com.example.sembakomobile.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.sembakomobile.R;

public class info_apk extends AppCompatActivity {
    ImageButton btn_kmbl;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_apk);

        btn_kmbl = findViewById(R.id.btn_kmbl);
        btn_kmbl.setOnClickListener(view -> onBackPressed());
    }
}