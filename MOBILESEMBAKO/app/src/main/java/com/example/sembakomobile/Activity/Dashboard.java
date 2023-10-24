package com.example.sembakomobile.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.sembakomobile.Fragment.Home;
import com.example.sembakomobile.Fragment.Setting;
import com.example.sembakomobile.R;
import com.example.sembakomobile.Fragment.Transaksi;
import com.example.sembakomobile.Fragment.History;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        BottomNavigationView btNav = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        btNav = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,new Home()).commit();

        btNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selected = null;
                switch (item.getItemId()) {
                    case R.id.firstFragment:
                        selected = new Home();
                        break;
                    case R.id.secondFragment:
                        selected = new Transaksi();
                        break;
                    case R.id.thirdFragment:
                        selected = new History();
                        break;
                    case R.id.fourthFragment:
                        selected = new Setting();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, selected).commit();
                return true;
            }
        });
    }
}
