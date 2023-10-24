package com.example.sembakomobile.Rincian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sembakomobile.API.ApiClient;
import com.example.sembakomobile.API.ServiceLogin;
import com.example.sembakomobile.Activity.SaveAccount;
import com.example.sembakomobile.Adapter.AdapterPending;
import com.example.sembakomobile.Adapter.AdapterSampai;
import com.example.sembakomobile.Model.Retrofit.DataDetailTransaksi;
import com.example.sembakomobile.Model.Retrofit.DataItem;
import com.example.sembakomobile.Model.Retrofit.DataKeranjang;
import com.example.sembakomobile.Model.Retrofit.ResponseDetailTransaksi;
import com.example.sembakomobile.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RincianSampai extends AppCompatActivity {
    private RecyclerView.Adapter rincian;
    List<DataKeranjang> listDataKeranjangSengDipilihUser;
    private List<DataDetailTransaksi> sampai = new ArrayList<>();
    private RecyclerView rv_rincian;
    int position;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rincian_sampai);
        TextView alamat, nama, nopesanan, tanggal, total;
        Button sampai;
        ImageButton kembali;

        rv_rincian = findViewById(R.id.rv_rincian);
        kembali = findViewById(R.id.kembali_rincian);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        nopesanan = findViewById(R.id.notrx_detail);

        tanggal = findViewById(R.id.tanggal_detail);

        sampai = findViewById(R.id.sampai_btpesan);
        sampai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        nama = findViewById(R.id.text_nama_pembeli);
        nama.setText(SaveAccount.readDataPembeli(RincianSampai.this).getNamaPembeli());

        alamat = findViewById(R.id.text_alamat);
        alamat.setText(SaveAccount.readDataPembeli(RincianSampai.this).getAlamat());

    }
    public void btnSampai() {
        ServiceLogin service1 = ApiClient.getClient().create(ServiceLogin.class);
        DataItem cri = SaveAccount.readDataPembeli(getApplicationContext().getApplicationContext());
        Call<ResponseDetailTransaksi> tampilData = service1.update_selesai(cri.getIdPembeli(), "selesai");
        tampilData.enqueue(new Callback<ResponseDetailTransaksi>() {
            @Override
            public void onResponse(Call<ResponseDetailTransaksi> call, Response<ResponseDetailTransaksi> response) {
                byte kode = response.body().getKode();
                System.out.println("res "+new Gson().toJson(response.body().getData()));
                String message = response.body().getMessage();

                System.out.println("datatrx "+listDataKeranjangSengDipilihUser);
                sampai = response.body().getData();
                rincian = new AdapterSampai(RincianSampai.this, sampai);
                rv_rincian.setAdapter(rincian);
                rincian.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseDetailTransaksi> call, Throwable t) {
            }
        });
    }

}