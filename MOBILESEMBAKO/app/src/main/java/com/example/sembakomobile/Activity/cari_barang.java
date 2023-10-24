package com.example.sembakomobile.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sembakomobile.API.ApiClient;
import com.example.sembakomobile.API.ServiceLogin;
import com.example.sembakomobile.Adapter.AdapterBarang;
import com.example.sembakomobile.Adapter.AdapterCariBarang;
import com.example.sembakomobile.Model.Retrofit.DataBarang;
import com.example.sembakomobile.Model.Retrofit.ResponseModelBarang;
import com.example.sembakomobile.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cari_barang extends AppCompatActivity {
    private RecyclerView rvData;
    private RecyclerView.LayoutManager lmData;
    private List<DataBarang> cariBarangList = new ArrayList<>();
    private SearchView searchView;
    AdapterCariBarang dataBarang;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_barang);
        ImageButton btn_back;

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(view -> onBackPressed());

        rvData = findViewById(R.id.rv_data2);
        lmData = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);

        searchView = findViewById(R.id.cari_barang);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ServiceLogin service1 = ApiClient.getClient().create(ServiceLogin.class);
                if(newText.equals("") ) {
                    rvData.setVisibility(View.INVISIBLE);
                } else if(newText.isEmpty()){
                    rvData.setVisibility(View.INVISIBLE);
                    View view = getLayoutInflater().inflate(R.layout.toast_nobarang, null);
                    view.findViewById(R.id.toast_nobarang);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view);
                    toast.show();
                    toast.setGravity(Gravity.BOTTOM| Gravity.CENTER,0,100);
                } else {
                    rvData.setVisibility(View.VISIBLE);
                }
                service1.cariBarangSpesifik(newText).enqueue(new Callback<ResponseModelBarang>() {
                    @Override
                    public void onResponse(Call<ResponseModelBarang> call, Response<ResponseModelBarang> response) {
                        cariBarangList = response.body().getData();
                        AdapterCariBarang.adapterBarangListener adapterBarangListener = new AdapterCariBarang.adapterBarangListener() {
                            @Override
                            public void selectedItemListener(int positionOfItemClicked) {
                                Intent intent  = new Intent(getApplicationContext(), detail_barang.class);
                                Gson gso1 = new Gson();
                                DataBarang dataBarang = cariBarangList.get(positionOfItemClicked);
                                String serializeObject = gso1.toJson(dataBarang);
                                intent.putExtra("KEY_DATA",serializeObject);
                                startActivity(intent);

//                        Toast.makeText(getActivity().getApplicationContext(),
//                                "Click item = " + dataBarangList.get(positionOfItemClicked).getGambar(), Toast.LENGTH_SHORT).show();
                            }
                        };
                        dataBarang = new AdapterCariBarang(cari_barang.this, cariBarangList, adapterBarangListener);
                        rvData.setAdapter(dataBarang);
                        dataBarang.notifyDataSetChanged();
                    }
                    @Override
                    public void onFailure(Call<ResponseModelBarang> call, Throwable t) {
                    }
                });
                return true;
            }
        });
    }
}